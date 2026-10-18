package com.acme.diamond.domain.mathematics.node;

import com.acme.diamond.domain.mathematics.event.Operand;
import com.telamin.fluxtion.runtime.annotations.OnEventHandler;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Holds the latest dividend n. Tells its children only when n is different from the value it already held. */
public class Dividend extends EventLogNode {
    private transient int value;

    @OnEventHandler
    public boolean onOperand(Operand operand) {
        boolean changed = operand.value() != value;
        value = operand.value();
        auditLog.info("n", operand.value()).info("value", value).info("changed", changed);
        return changed;
    }

    public int value() {
        return value;
    }
}
