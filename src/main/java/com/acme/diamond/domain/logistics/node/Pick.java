package com.acme.diamond.domain.logistics.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Picker trips: a trolley holds 20 items, so trips = ceil(items / 20). Tells its children only when it changes. */
public class Pick extends EventLogNode {
    private final CurrentOrder order;
    private transient int trips;

    public Pick(CurrentOrder order) {
        this.order = order;
    }

    @OnTrigger
    public boolean recalculate() {
        int next = Math.ceilDiv(order.items(), 20);
        boolean changed = next != trips;
        trips = next;
        auditLog.info("items", order.items()).info("trips", trips).info("changed", changed);
        return changed;
    }

    public int trips() {
        return trips;
    }
}
