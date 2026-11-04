package org.openindustries.eretail.storeinv;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class StoreinvService {

    @Inject
    StoreinvRepository repository;

    public StoreInventory open(OpenStoreInventoryRequest request) {
        return repository.saveAccount(StoreInventory.open(request.sku(), request.availableUnits()));
    }

    public StoreInventory get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "StoreInventory not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public StoreReservation consume(UUID accountId, CreateStoreReservationRequest request) {
        StoreInventory account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "StoreInventory is not ACTIVE");
        }
        if (request.units() > account.availableUnits) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.units()
                    + " exceeds available " + account.availableUnits);
        }
        account.availableUnits -= request.units();
        repository.saveAccount(account);
        return repository.saveRecord(StoreReservation.of(accountId, request.channel(), request.units(), account.availableUnits));
    }

    public List<StoreReservation> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
