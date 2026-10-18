package com.acme.diamond.domain.logistics.builder;

import com.acme.diamond.domain.logistics.node.CurrentOrder;
import com.acme.diamond.domain.logistics.node.Pick;
import com.acme.diamond.domain.logistics.node.Pack;
import com.acme.diamond.domain.logistics.node.Dispatch;
import com.telamin.fluxtion.builder.compile.config.FluxtionCompilerConfig;
import com.telamin.fluxtion.builder.compile.config.FluxtionGraphBuilder;
import com.telamin.fluxtion.builder.generation.config.EventProcessorConfig;
import com.telamin.fluxtion.runtime.audit.EventLogControlEvent.LogLevel;

/**
 * The diamond in order fulfilment: order -> pick, order -> pack, pick -> dispatch, pack -> dispatch. Each node names only its parents,
 * in its constructor; the compiler works out the dispatch order. The names given here are the node names in
 * the audit log.
 */
public class LogisticsBuilder implements FluxtionGraphBuilder {
    @Override
    public void buildGraph(EventProcessorConfig cfg) {
        cfg.addEventAudit(LogLevel.INFO);
        CurrentOrder order = cfg.addNode(new CurrentOrder(), "order");
        Pick pick = cfg.addNode(new Pick(order), "pick");
        Pack pack = cfg.addNode(new Pack(order), "pack");
        cfg.addNode(new Dispatch(pick, pack), "dispatch");
    }

    @Override
    public void configureGeneration(FluxtionCompilerConfig cfg) {
        cfg.setPackageName("com.acme.diamond.domain.logistics.generated");
        cfg.setClassName("LogisticsProcessor");
        cfg.setWriteSourceToFile(true);
        cfg.setWriteGraphMlToFile(true);
        cfg.setFormatSource(true);
    }
}
