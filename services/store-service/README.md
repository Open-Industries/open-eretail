# Store Service

Bounded context: **Stores** — Store registry: formats and regions.

Alignment: Retail network master data. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/stores` |
| Owns data | Store records |
| Publishes | `store.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/store-service quarkus:dev`.
