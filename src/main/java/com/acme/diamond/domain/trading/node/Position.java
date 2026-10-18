package com.acme.diamond.domain.trading.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** The desk holds 10 units: the position's market value is 10 * price. Tells its children only when it changes. */
public class Position extends EventLogNode {
    public static final int QUANTITY = 10;
    private final LastTrade trade;
    private transient int marketValue;

    public Position(LastTrade trade) {
        this.trade = trade;
    }

    @OnTrigger
    public boolean recalculate() {
        int next = QUANTITY * trade.price();
        boolean changed = next != marketValue;
        marketValue = next;
        auditLog.info("price", trade.price()).info("quantity", QUANTITY).info("marketValue", marketValue)
                .info("changed", changed);
        return changed;
    }

    public int marketValue() {
        return marketValue;
    }
}
