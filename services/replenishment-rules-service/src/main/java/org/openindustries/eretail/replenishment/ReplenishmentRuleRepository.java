package org.openindustries.eretail.replenishment;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class ReplenishmentRuleRepository {
    private final ConcurrentHashMap<UUID, ReplenishmentRule> store = new ConcurrentHashMap<>();

    public ReplenishmentRule save(ReplenishmentRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<ReplenishmentRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<ReplenishmentRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<ReplenishmentRule> activeFor(String category) {
        return store.values().stream()
                .filter(r -> r.category.equals(category) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
