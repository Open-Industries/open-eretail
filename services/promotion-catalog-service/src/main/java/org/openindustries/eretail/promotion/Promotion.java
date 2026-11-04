package org.openindustries.eretail.promotion;

import java.util.UUID;

/** Promotion aggregate — Promotion catalogue: discounts, bundles and loyalty offers. */
public class Promotion {
    public UUID id;
        public String name;
        public String promoType;
        public int discountPct;
    public String status;

    public static Promotion create(String name, String promoType, int discountPct) {
        Promotion e = new Promotion();
        e.id = UUID.randomUUID();
        e.name = name;
        e.promoType = promoType;
        e.discountPct = discountPct;
        e.status = "DRAFT";
        return e;
    }
}
