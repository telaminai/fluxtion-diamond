package com.acme.diamond.domain.accounting.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** A 10% volume discount on lines with a net amount of 1000 or more, otherwise 0. Tells its children only when it changes. */
public class Discount extends EventLogNode {
    private final Line line;
    private transient int discount;

    public Discount(Line line) {
        this.line = line;
    }

    @OnTrigger
    public boolean recalculate() {
        int next = line.net() >= 1000 ? Math.floorDiv(line.net() * 10, 100) : 0;
        boolean changed = next != discount;
        discount = next;
        auditLog.info("net", line.net()).info("discount", discount).info("changed", changed);
        return changed;
    }

    public int discount() {
        return discount;
    }
}
