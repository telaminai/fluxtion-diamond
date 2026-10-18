package com.acme.diamond.domain.mathematics.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** r = n mod 3. Tells its children only when r changes. */
public class Remainder extends EventLogNode {
    private final Dividend dividend;
    private transient int value;

    public Remainder(Dividend dividend) {
        this.dividend = dividend;
    }

    @OnTrigger
    public boolean recalculate() {
        int next = Math.floorMod(dividend.value(), 3);
        boolean changed = next != value;
        value = next;
        auditLog.info("n", dividend.value()).info("value", value).info("changed", changed);
        return changed;
    }

    public int value() {
        return value;
    }
}
