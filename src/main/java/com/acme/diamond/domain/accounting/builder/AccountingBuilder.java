package com.acme.diamond.domain.accounting.builder;

import com.acme.diamond.domain.accounting.node.Line;
import com.acme.diamond.domain.accounting.node.Tax;
import com.acme.diamond.domain.accounting.node.Discount;
import com.acme.diamond.domain.accounting.node.Total;
import com.telamin.fluxtion.builder.compile.config.FluxtionCompilerConfig;
import com.telamin.fluxtion.builder.compile.config.FluxtionGraphBuilder;
import com.telamin.fluxtion.builder.generation.config.EventProcessorConfig;
import com.telamin.fluxtion.runtime.audit.EventLogControlEvent.LogLevel;

/**
 * The diamond in invoicing: line -> tax, line -> discount, tax -> total, discount -> total. Each node names only its parents,
 * in its constructor; the compiler works out the dispatch order. The names given here are the node names in
 * the audit log.
 */
public class AccountingBuilder implements FluxtionGraphBuilder {
    @Override
    public void buildGraph(EventProcessorConfig cfg) {
        cfg.addEventAudit(LogLevel.INFO);
        Line line = cfg.addNode(new Line(), "line");
        Tax tax = cfg.addNode(new Tax(line), "tax");
        Discount discount = cfg.addNode(new Discount(line), "discount");
        cfg.addNode(new Total(tax, discount), "total");
    }

    @Override
    public void configureGeneration(FluxtionCompilerConfig cfg) {
        cfg.setPackageName("com.acme.diamond.domain.accounting.generated");
        cfg.setClassName("AccountingProcessor");
        cfg.setWriteSourceToFile(true);
        cfg.setWriteGraphMlToFile(true);
        cfg.setFormatSource(true);
    }
}
