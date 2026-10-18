package com.acme.diamond.domain.accounting.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** The amount to invoice: gross - discount. Counts how many times it has been run. */
public class Total extends EventLogNode {
    private final Tax tax;
    private final Discount discount;
    private transient int total;
    private transient int invocations;

    public Total(Tax tax, Discount discount) {
        this.tax = tax;
        this.discount = discount;
    }

    @OnTrigger
    public boolean recalculate() {
        invocations++;
        total = tax.gross() - discount.discount();
        auditLog.info("gross", tax.gross()).info("discount", discount.discount())
                .info("total", total).info("invocations", invocations);
        return true;
    }

    public int total() {
        return total;
    }

    public int invocations() {
        return invocations;
    }
}
