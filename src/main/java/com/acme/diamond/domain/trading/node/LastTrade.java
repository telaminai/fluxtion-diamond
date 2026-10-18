package com.acme.diamond.domain.trading.node;

import com.acme.diamond.domain.trading.event.Trade;
import com.telamin.fluxtion.runtime.annotations.OnEventHandler;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Holds the last trade price. Tells its children only when it is different from the price it held. */
public class LastTrade extends EventLogNode {
    private transient int price;

    @OnEventHandler
    public boolean onTrade(Trade trade) {
        boolean changed = trade.price() != price;
        price = trade.price();
        auditLog.info("price", price).info("changed", changed);
        return changed;
    }

    public int price() {
        return price;
    }
}
