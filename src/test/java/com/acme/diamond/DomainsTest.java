package com.acme.diamond;

import com.acme.diamond.host.DomainMain;
import com.acme.diamond.host.DomainMain.Domain;
import com.telamin.fluxtion.runtime.DataFlow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Each domain variant has the generic diamond's shape and its skip: the repeated input runs only the top node. */
class DomainsTest {

    @ParameterizedTest
    @ValueSource(strings = {"mathematics", "accounting", "logistics", "trading"})
    void bottomRunsOncePerChangingInputAndNotForTheRepeat(String id) throws Exception {
        check(DomainMain.domain(id));
    }

    private static <D> void check(Domain<D> domain) throws Exception {
        DataFlow flow = (DataFlow) Class.forName(domain.processor()).getDeclaredConstructor().newInstance();
        List<String> records = new ArrayList<>();
        flow.setAuditLogProcessor(r -> records.add(r.toString()));
        flow.init();
        D bottom = flow.getNodeById(domain.bottom());

        int[] runs = {1, 1, 0, 1};
        int[] seq = domain.sequence();
        assertEquals(seq[1], seq[2], "the third input repeats the second");
        for (int i = 0; i < seq.length; i++) {
            int before = domain.invocations().applyAsInt(bottom);
            flow.onEvent(domain.event().apply(seq[i]));
            assertEquals(runs[i], domain.invocations().applyAsInt(bottom) - before,
                    domain.bottom() + " runs for input " + seq[i]);
        }

        List<String> events = records.stream().filter(r -> r.contains("nodeLogs: \n        - ")).toList();
        assertEquals(4, events.size(), "one audit record with node entries per input");
        String repeat = events.get(2);
        assertEquals(1, repeat.split("\n        - ").length - 1, "repeated input: one node entry");
        assertTrue(repeat.contains("changed: false"), "repeated input: the top node saw no change");
        assertFalse(repeat.contains("- " + domain.bottom() + ":"), "repeated input: " + domain.bottom() + " did not run");
        assertTrue(events.get(1).contains("- " + domain.bottom() + ":"), "second input: " + domain.bottom() + " ran");
    }
}
