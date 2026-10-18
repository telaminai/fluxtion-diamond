package com.acme.diamond.domain.mathematics.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** q = floor(n / 3). Tells its children only when q changes. */
public class Quotient extends EventLogNode {
    private final Dividend dividend;
    private transient int value;

    public Quotient(Dividend dividend) {
        this.dividend = dividend;
    }

    @OnTrigger
    public boolean recalculate() {
        int next = Math.floorDiv(dividend.value(), 3);
        boolean changed = next != value;
        value = next;
        auditLog.info("n", dividend.value()).info("value", value).info("changed", changed);
        return changed;
    }

    public int value() {
        return value;
    }
}
