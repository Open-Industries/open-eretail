# Promotion Catalog Service

Bounded context: **Promotions** — Promotion catalogue: discounts, bundles and loyalty offers.

Alignment: Commercial catalogue data. Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/promotions` |
| Owns data | Promotion records |

Run locally: `mvn -pl services/promotion-catalog-service quarkus:dev`.
