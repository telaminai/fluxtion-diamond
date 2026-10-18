package com.acme.diamond.domain.logistics.event;

/** The one event this graph handles: an order, as the number of items on it. */
public record Order(int items) {
}
