package com.acme.diamond.domain.accounting.event;

/** The one event this graph handles: an invoice line's net amount, in whole currency units. */
public record InvoiceLine(int net) {
}
