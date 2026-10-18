package com.acme.diamond.domain.trading.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Unrealised P&L of the 10 units, bought at 100: 10 * (price - 100). Tells its children only when it changes. */
public class Pnl extends EventLogNode {
    public static final int COST = 100;
    private final LastTrade trade;
    private transient int pnl;

    public Pnl(LastTrade trade) {
        this.trade = trade;
    }

    @OnTrigger
    public boolean recalculate() {
        int next = Position.QUANTITY * (trade.price() - COST);
        boolean changed = next != pnl;
        pnl = next;
        auditLog.info("price", trade.price()).info("pnl", pnl).info("changed", changed);
        return changed;
    }

    public int pnl() {
        return pnl;
    }
}
