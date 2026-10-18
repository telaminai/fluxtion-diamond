package com.acme.diamond.domain.logistics.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Cartons: a carton holds 6 items, so cartons = ceil(items / 6). Tells its children only when it changes. */
public class Pack extends EventLogNode {
    private final CurrentOrder order;
    private transient int cartons;

    public Pack(CurrentOrder order) {
        this.order = order;
    }

    @OnTrigger
    public boolean recalculate() {
        int next = Math.ceilDiv(order.items(), 6);
        boolean changed = next != cartons;
        cartons = next;
        auditLog.info("items", order.items()).info("cartons", cartons).info("changed", changed);
        return changed;
    }

    public int cartons() {
        return cartons;
    }
}
