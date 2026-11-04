# Replenishment Rules Service

Bounded context: **Replenishment Rules** — Decision rules binding categories to safety-stock and shrinkage envelopes.

Alignment: Replenishment policy as auditable rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/replenishment-rules` |
| Owns data | ReplenishmentRule rules |
| Publishes | `replenishment.activated` |

Run locally: `mvn -pl services/replenishment-rules-service quarkus:dev`.
