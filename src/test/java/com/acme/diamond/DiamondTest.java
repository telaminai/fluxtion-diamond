package com.acme.diamond;

import com.acme.diamond.event.X;
import com.acme.diamond.node.D;
import com.telamin.fluxtion.runtime.DataFlow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Drives a fresh generated processor with 2, 3, 3, 4 and checks D and the audit records. */
class DiamondTest {

    @ParameterizedTest
    @ValueSource(strings = {"com.acme.diamond.generated.DiamondProcessor",
            "com.acme.diamond.generated.spring.DiamondSpringProcessor"})
    void dRunsOncePerChangingEventAndNotForARepeat(String processor) throws Exception {
        DataFlow flow = (DataFlow) Class.forName(processor).getDeclaredConstructor().newInstance();
        List<String> records = new ArrayList<>();
        flow.setAuditLogProcessor(r -> records.add(r.toString()));
        flow.init();
        D d = flow.getNodeById("d");

        int[] xs = {2, 3, 3, 4};
        int[] runs = {1, 1, 0, 1};
        int[] values = {106, 109, 109, 212};
        for (int i = 0; i < xs.length; i++) {
            int before = d.invocations();
            flow.onEvent(new X(xs[i]));
            assertEquals(runs[i], d.invocations() - before, "D runs for x=" + xs[i]);
            assertEquals(values[i], d.value(), "D value after x=" + xs[i]);
        }

        List<String> events = records.stream().filter(r -> r.contains("event: X")).toList();
        assertEquals(4, events.size(), "one audit record per X event");
        assertTrue(events.get(0).contains("- d:"), "the generated processor emitted node audit entries");
        assertTrue(events.get(1).contains("changed: false") && events.get(1).contains("- d:"),
                "x=3 after 2: B unchanged, C changed, D ran");
        assertTrue(events.get(2).contains("- a:") && !events.get(2).contains("- b:")
                && !events.get(2).contains("- c:") && !events.get(2).contains("- d:"),
                "repeated 3: only A ran");
    }
}
