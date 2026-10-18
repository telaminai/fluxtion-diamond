package com.acme.diamond.domain.logistics.node;

import com.acme.diamond.domain.logistics.event.Order;
import com.telamin.fluxtion.runtime.annotations.OnEventHandler;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Holds the latest order's item count. Tells its children only when it is different from the one it held. */
public class CurrentOrder extends EventLogNode {
    private transient int items;

    @OnEventHandler
    public boolean onOrder(Order order) {
        boolean changed = order.items() != items;
        items = order.items();
        auditLog.info("items", items).info("changed", changed);
        return changed;
    }

    public int items() {
        return items;
    }
}
