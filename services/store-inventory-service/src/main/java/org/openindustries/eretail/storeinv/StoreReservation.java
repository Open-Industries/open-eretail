package org.openindustries.eretail.storeinv;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a StoreInventory. */
public class StoreReservation {
    public UUID id;
    public UUID accountId;
    public String channel;
    public long units;
    public long remainingAfter;
    public Instant recordedAt;

    public static StoreReservation of(UUID accountId, String channel, long units, long remainingAfter) {
        StoreReservation r = new StoreReservation();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.channel = channel;
        r.units = units;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
