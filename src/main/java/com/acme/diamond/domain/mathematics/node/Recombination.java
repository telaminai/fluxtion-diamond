package com.acme.diamond.domain.mathematics.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** 3q + r, which gives back n (the division identity). Counts how many times it has been run. */
public class Recombination extends EventLogNode {
    private final Quotient quotient;
    private final Remainder remainder;
    private transient int value;
    private transient int invocations;

    public Recombination(Quotient quotient, Remainder remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }

    @OnTrigger
    public boolean recalculate() {
        invocations++;
        value = 3 * quotient.value() + remainder.value();
        auditLog.info("quotient", quotient.value()).info("remainder", remainder.value())
                .info("value", value).info("invocations", invocations);
        return true;
    }

    public int value() {
        return value;
    }

    public int invocations() {
        return invocations;
    }
}
