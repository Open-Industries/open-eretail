package org.openindustries.eretail.storeinv;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class StoreinvRepository {
    private final ConcurrentHashMap<UUID, StoreInventory> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<StoreReservation> records = new CopyOnWriteArrayList<>();

    public StoreInventory saveAccount(StoreInventory account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<StoreInventory> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public StoreReservation saveRecord(StoreReservation record) {
        records.add(record);
        return record;
    }

    public List<StoreReservation> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
