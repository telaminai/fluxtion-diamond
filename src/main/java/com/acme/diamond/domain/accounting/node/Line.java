package com.acme.diamond.domain.accounting.node;

import com.acme.diamond.domain.accounting.event.InvoiceLine;
import com.telamin.fluxtion.runtime.annotations.OnEventHandler;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Holds the latest invoice line's net amount. Tells its children only when it is different from the one it held. */
public class Line extends EventLogNode {
    private transient int net;

    @OnEventHandler
    public boolean onInvoiceLine(InvoiceLine line) {
        boolean changed = line.net() != net;
        net = line.net();
        auditLog.info("net", net).info("changed", changed);
        return changed;
    }

    public int net() {
        return net;
    }
}
