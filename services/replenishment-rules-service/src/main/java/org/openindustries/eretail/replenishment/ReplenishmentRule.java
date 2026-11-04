package org.openindustries.eretail.replenishment;

import java.util.UUID;

/**
 * ReplenishmentRule aggregate — a decision rule of this core: for one category, it guarantees a
 * capacity (safetyStockUnits) and a threshold (maxShrinkagePct), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class ReplenishmentRule {
    public UUID id;
    public String name;
    public String category;
    public long safetyStockUnits;
    public int maxShrinkagePct;
    public int priority;
    public String status;

    public static ReplenishmentRule create(String name, String category, long safetyStockUnits, int maxShrinkagePct, int priority) {
        ReplenishmentRule r = new ReplenishmentRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.category = category;
        r.safetyStockUnits = safetyStockUnits;
        r.maxShrinkagePct = maxShrinkagePct;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
