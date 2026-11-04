# Store Inventory Service

Bounded context: **Store Inventory** — Guarded per-store SKU stock and the channel reservations against it.

Alignment: Store-level inventory integrity. Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/store-inventory` |
| Owns data | StoreInventory accounts, StoreReservation records |
| Publishes | `storeinv.consumed`, `storeinv.insufficient` |

Run locally: `mvn -pl services/store-inventory-service quarkus:dev`.
