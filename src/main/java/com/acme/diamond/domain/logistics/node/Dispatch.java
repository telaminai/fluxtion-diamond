package com.acme.diamond.domain.logistics.node;

import com.telamin.fluxtion.runtime.annotations.OnTrigger;
import com.telamin.fluxtion.runtime.audit.EventLogNode;

/** Books the dispatch slot: 10 minutes per picker trip + 3 per carton. Counts how many times it has been run. */
public class Dispatch extends EventLogNode {
    private final Pick pick;
    private final Pack pack;
    private transient int minutes;
    private transient int invocations;

    public Dispatch(Pick pick, Pack pack) {
        this.pick = pick;
        this.pack = pack;
    }

    @OnTrigger
    public boolean recalculate() {
        invocations++;
        minutes = 10 * pick.trips() + 3 * pack.cartons();
        auditLog.info("trips", pick.trips()).info("cartons", pack.cartons())
                .info("minutes", minutes).info("invocations", invocations);
        return true;
    }

    public int minutes() {
        return minutes;
    }

    public int invocations() {
        return invocations;
    }
}
