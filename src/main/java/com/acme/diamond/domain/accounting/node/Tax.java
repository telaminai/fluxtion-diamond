package com.acme.diamond.domain.accounting.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Sales tax at 20% of the net amount, and the gross amount (net + tax). Tells its children only when gross changes. */
public class Tax extends EventLogNode {
    private final Line line;
    private transient int tax;
    private transient int gross;

    public Tax(Line line) {
        this.line = line;
    }

    @OnTrigger
    public boolean recalculate() {
        int nextTax = Math.floorDiv(line.net() * 20, 100);
        int nextGross = line.net() + nextTax;
        boolean changed = nextGross != gross;
        tax = nextTax;
        gross = nextGross;
        auditLog.info("net", line.net()).info("tax", tax).info("gross", gross).info("changed", changed);
        return changed;
    }

    public int tax() {
        return tax;
    }

    public int gross() {
        return gross;
    }
}
