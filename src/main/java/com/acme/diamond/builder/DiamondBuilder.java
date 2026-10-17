package com.acme.diamond.builder;

import com.acme.diamond.node.A;
import com.acme.diamond.node.B;
import com.acme.diamond.node.C;
import com.acme.diamond.node.D;
import com.telamin.fluxtion.builder.compile.config.FluxtionCompilerConfig;
import com.telamin.fluxtion.builder.compile.config.FluxtionGraphBuilder;
import com.telamin.fluxtion.builder.generation.config.EventProcessorConfig;
import com.telamin.fluxtion.runtime.audit.EventLogControlEvent.LogLevel;

/**
 * The diamond A -> B, A -> C, B -> D, C -> D. Each node names only its parents, in its constructor;
 * the compiler works out the dispatch order. The names given here are the node names in the audit log.
 */
public class DiamondBuilder implements FluxtionGraphBuilder {
    @Override
    public void buildGraph(EventProcessorConfig cfg) {
        cfg.addEventAudit(LogLevel.INFO);
        A a = cfg.addNode(new A(), "a");
        B b = cfg.addNode(new B(a), "b");
        C c = cfg.addNode(new C(a), "c");
        cfg.addNode(new D(b, c), "d");
    }

    @Override
    public void configureGeneration(FluxtionCompilerConfig cfg) {
        cfg.setPackageName("com.acme.diamond.generated");
        cfg.setClassName("DiamondProcessor");
        cfg.setWriteSourceToFile(true);
        cfg.setWriteGraphMlToFile(true);
        cfg.setFormatSource(true);
    }
}
