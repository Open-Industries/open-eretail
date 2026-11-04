package org.openindustries.eretail.promotion;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class PromotionRepository {
    private final ConcurrentHashMap<UUID, Promotion> store = new ConcurrentHashMap<>();

    public Promotion save(Promotion entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<Promotion> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Promotion> findAll() {
        return List.copyOf(store.values());
    }
}
