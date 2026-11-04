# Open Eretail — Reference Omnichannel Retail Core Platform

A **standard, generic omnichannel retail (physical + digital) core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> a store's sellable stock is never oversold across POS, click-and-collect and delivery — a reservation that exceeds available units is rejected (422) and records nothing.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| GS1 (GTIN/SSCC) | Product and logistic-unit identification | SKU/SSCC identifier posture |
| PCI DSS | Cardholder data protection | Boundary: POS payments via certified providers only |
| WCAG 2.2 | Accessibility | Accessible contract posture for shopper-facing APIs |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `store-service` | 8081 | Retail network master data | Store registry: formats and regions. |
| `store-inventory-service` | 8082 | Store-level inventory integrity | Guarded per-store SKU stock and the channel reservations against it. |
| `replenishment-rules-service` | 8083 | Replenishment policy as auditable rules | Decision rules binding categories to safety-stock and shrinkage envelopes. |
| `promotion-catalog-service` | 8084 | Commercial catalogue data | Promotion catalogue: discounts, bundles and loyalty offers. |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `shrinkage-risk-scorer` | Event / HTTP | Scores store shrinkage signals (shrinkage, discrepancies, incidents) with an auditable rationale. |
| `store-sla-evaluator` | Event / HTTP | Compares queue times and on-shelf availability against store targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — a store's sellable stock is never oversold across POS, click-and-collect and delivery — a reservation that exceeds available units is rejected (422) and records nothing.
3. **Privacy by design** — Customer personal data is minimized and stays in adopter CRM systems; this core works with store, stock and promotion data. Card data never enters the platform (certified payment boundary).
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/store-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `replenishment-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `store-inventory-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
