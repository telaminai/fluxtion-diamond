package com.acme.diamond.domain.mathematics.builder;

import com.acme.diamond.domain.mathematics.node.Dividend;
import com.acme.diamond.domain.mathematics.node.Quotient;
import com.acme.diamond.domain.mathematics.node.Remainder;
import com.acme.diamond.domain.mathematics.node.Recombination;
import com.telamin.fluxtion.builder.compile.config.FluxtionCompilerConfig;
import com.telamin.fluxtion.builder.compile.config.FluxtionGraphBuilder;
import com.telamin.fluxtion.builder.generation.config.EventProcessorConfig;
import com.telamin.fluxtion.runtime.audit.EventLogControlEvent.LogLevel;

/**
 * The diamond in integer division: dividend -> quotient, dividend -> remainder, quotient -> recombination, remainder -> recombination. Each node names only its parents,
 * in its constructor; the compiler works out the dispatch order. The names given here are the node names in
 * the audit log.
 */
public class MathematicsBuilder implements FluxtionGraphBuilder {
    @Override
    public void buildGraph(EventProcessorConfig cfg) {
        cfg.addEventAudit(LogLevel.INFO);
        Dividend dividend = cfg.addNode(new Dividend(), "dividend");
        Quotient quotient = cfg.addNode(new Quotient(dividend), "quotient");
        Remainder remainder = cfg.addNode(new Remainder(dividend), "remainder");
        cfg.addNode(new Recombination(quotient, remainder), "recombination");
    }

    @Override
    public void configureGeneration(FluxtionCompilerConfig cfg) {
        cfg.setPackageName("com.acme.diamond.domain.mathematics.generated");
        cfg.setClassName("MathematicsProcessor");
        cfg.setWriteSourceToFile(true);
        cfg.setWriteGraphMlToFile(true);
        cfg.setFormatSource(true);
    }
}
