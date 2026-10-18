package com.acme.diamond.domain.trading.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Margin required: 20% of market value, plus any unrealised loss. Counts how many times it has been run. */
public class Risk extends EventLogNode {
    private final Position position;
    private final Pnl pnl;
    private transient int margin;
    private transient int invocations;

    public Risk(Position position, Pnl pnl) {
        this.position = position;
        this.pnl = pnl;
    }

    @OnTrigger
    public boolean recalculate() {
        invocations++;
        margin = Math.floorDiv(position.marketValue() * 20, 100) + Math.max(0, -pnl.pnl());
        auditLog.info("marketValue", position.marketValue()).info("pnl", pnl.pnl())
                .info("margin", margin).info("invocations", invocations);
        return true;
    }

    public int margin() {
        return margin;
    }

    public int invocations() {
        return invocations;
    }
}
