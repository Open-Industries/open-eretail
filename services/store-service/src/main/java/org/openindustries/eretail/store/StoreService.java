package org.openindustries.eretail.store;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class StoreService {

    @Inject
    StoreRepository repository;

    public Store create(CreateStoreRequest request) {
        return repository.save(Store.create(request.name(), request.format(), request.regionCode()));
    }

    public Store get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Store not found: " + id));
    }

    public List<Store> list() {
        return repository.findAll();
    }

    public Store suspend(UUID id) {
        Store entity = get(id);
        if ("SUSPENDED".equals(entity.status)) {
            throw new DomainException(409, "Store already SUSPENDED");
        }
        entity.status = "SUSPENDED";
        return repository.save(entity);
    }

    public Store reactivate(UUID id) {
        Store entity = get(id);
        if ("ACTIVE".equals(entity.status)) {
            throw new DomainException(409, "Store already ACTIVE");
        }
        entity.status = "ACTIVE";
        return repository.save(entity);
    }
}
