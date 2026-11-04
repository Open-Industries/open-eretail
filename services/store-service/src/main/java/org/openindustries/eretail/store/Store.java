package org.openindustries.eretail.store;

import java.time.Instant;
import java.util.UUID;

/**
 * Store aggregate — Store registry: formats and regions.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class Store {
    public UUID id;
        public String name;
        public String format;
        public String regionCode;
    public String status;
    public Instant createdAt;

    public static Store create(String name, String format, String regionCode) {
        Store e = new Store();
        e.id = UUID.randomUUID();
        e.name = name;
        e.format = format;
        e.regionCode = regionCode;
        e.status = "ACTIVE";
        e.createdAt = Instant.now();
        return e;
    }
}
