package com.acme.diamond.host;

import com.acme.diamond.domain.accounting.event.InvoiceLine;
import com.acme.diamond.domain.accounting.node.Total;
import com.acme.diamond.domain.logistics.event.Order;
import com.acme.diamond.domain.logistics.node.Dispatch;
import com.acme.diamond.domain.mathematics.event.Operand;
import com.acme.diamond.domain.mathematics.node.Recombination;
import com.acme.diamond.domain.trading.event.Trade;
import com.acme.diamond.domain.trading.node.Risk;
import com.telamin.fluxtion.runtime.DataFlow;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/**
 * Runs one domain variant of the diamond: four inputs, the third a repeat of the second, through that domain's
 * generated processor, and writes its audit log.
 *
 * <pre>java -cp target/fluxtion-diamond.jar com.acme.diamond.host.DomainMain &lt;domain&gt; [out/&lt;domain&gt;-audit.yaml]</pre>
 */
public final class DomainMain {

    /** One domain: its processor, the name of its bottom node, the inputs, and how to read the bottom node. */
    public record Domain<D>(String id, String processor, String bottom, int[] sequence,
                            IntFunction<Object> event, ToIntFunction<D> value, ToIntFunction<D> invocations) {
    }

    public static final List<Domain<?>> DOMAINS = List.of(
            new Domain<Recombination>("mathematics", "com.acme.diamond.domain.mathematics.generated.MathematicsProcessor",
                    "recombination", new int[]{4, 5, 5, 7}, Operand::new, Recombination::value, Recombination::invocations),
            new Domain<Total>("accounting", "com.acme.diamond.domain.accounting.generated.AccountingProcessor",
                    "total", new int[]{400, 600, 600, 1200}, InvoiceLine::new, Total::total, Total::invocations),
            new Domain<Dispatch>("logistics", "com.acme.diamond.domain.logistics.generated.LogisticsProcessor",
                    "dispatch", new int[]{12, 18, 18, 30}, Order::new, Dispatch::minutes, Dispatch::invocations),
            new Domain<Risk>("trading", "com.acme.diamond.domain.trading.generated.TradingProcessor",
                    "risk", new int[]{98, 103, 103, 101}, Trade::new, Risk::margin, Risk::invocations));

    private DomainMain() {
    }

    public static Domain<?> domain(String id) {
        return DOMAINS.stream().filter(d -> d.id().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown domain " + id + "; one of "
                        + DOMAINS.stream().map(Domain::id).toList()));
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("usage: DomainMain <" + String.join("|", DOMAINS.stream().map(Domain::id).toList())
                    + "> [audit file]");
            System.exit(2);
        }
        Domain<?> domain = domain(args[0]);
        Path file = Path.of(args.length > 1 ? args[1] : "out/" + domain.id() + "-audit.yaml").toAbsolutePath();
        Files.createDirectories(file.getParent());
        run(domain, file);
    }

    private static <D> void run(Domain<D> domain, Path file) throws Exception {
        // loaded by name: the class is generated at build time, so nothing here imports it
        DataFlow flow = (DataFlow) Class.forName(domain.processor()).getDeclaredConstructor().newInstance();
        try (Writer out = Files.newBufferedWriter(file)) {
            YamlAuditFile audit = new YamlAuditFile(out);
            flow.setAuditLogProcessor(audit);   // before init(), so the Init record reaches the file
            flow.init();
            D bottom = flow.getNodeById(domain.bottom());
            for (int input : domain.sequence()) {
                int before = domain.invocations().applyAsInt(bottom);
                flow.onEvent(domain.event().apply(input));
                System.out.printf("input=%d  %s=%d  %s ran %d time(s) for this event%n", input,
                        domain.bottom(), domain.value().applyAsInt(bottom), domain.bottom(),
                        domain.invocations().applyAsInt(bottom) - before);
            }
            audit.close();
            System.out.printf("%d audit records -> %s%n", audit.records(), file.getFileName());
        }
    }
}
