package com.acme.diamond.domain.trading.builder;

import com.acme.diamond.domain.trading.node.LastTrade;
import com.acme.diamond.domain.trading.node.Position;
import com.acme.diamond.domain.trading.node.Pnl;
import com.acme.diamond.domain.trading.node.Risk;
import com.telamin.fluxtion.builder.compile.config.FluxtionCompilerConfig;
import com.telamin.fluxtion.builder.compile.config.FluxtionGraphBuilder;
import com.telamin.fluxtion.builder.generation.config.EventProcessorConfig;
import com.telamin.fluxtion.runtime.audit.EventLogControlEvent.LogLevel;

/**
 * The diamond in trade processing: trade -> position, trade -> pnl, position -> risk, pnl -> risk. Each node names only its parents,
 * in its constructor; the compiler works out the dispatch order. The names given here are the node names in
 * the audit log.
 */
public class TradingBuilder implements FluxtionGraphBuilder {
    @Override
    public void buildGraph(EventProcessorConfig cfg) {
        cfg.addEventAudit(LogLevel.INFO);
        LastTrade trade = cfg.addNode(new LastTrade(), "trade");
        Position position = cfg.addNode(new Position(trade), "position");
        Pnl pnl = cfg.addNode(new Pnl(trade), "pnl");
        cfg.addNode(new Risk(position, pnl), "risk");
    }

    @Override
    public void configureGeneration(FluxtionCompilerConfig cfg) {
        cfg.setPackageName("com.acme.diamond.domain.trading.generated");
        cfg.setClassName("TradingProcessor");
        cfg.setWriteSourceToFile(true);
        cfg.setWriteGraphMlToFile(true);
        cfg.setFormatSource(true);
    }
}
