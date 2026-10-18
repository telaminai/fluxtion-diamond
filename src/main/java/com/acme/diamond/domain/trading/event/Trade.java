package com.acme.diamond.domain.trading.event;

/** The one event this graph handles: a trade printed in the instrument the desk holds, at this price. */
public record Trade(int price) {
}
