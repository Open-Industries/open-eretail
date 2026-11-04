# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Stores → Store Inventory → Replenishment Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `store-service` | 8081 | Retail network master data |
| `store-inventory-service` | 8082 | Store-level inventory integrity |
| `replenishment-rules-service` | 8083 | Replenishment policy as auditable rules |
| `promotion-catalog-service` | 8084 | Commercial catalogue data |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/store-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
