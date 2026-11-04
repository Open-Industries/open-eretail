# Domain Standards — Omnichannel Retail

Alignment mapping for this core. *Aligned to* throughout.

## Concept mapping

| Platform concept | Domain standard | Mapping |
|---|---|---|
| Product/logistic identity | GS1 GTIN / SSCC | SKU and shipment identifier posture |
| Store stock integrity | Platform rule | The store-level no-oversell invariant across channels |
| Payments | PCI DSS | Out of scope: certified POS/payment providers only |
| Shopper-facing contracts | WCAG 2.2 posture | Accessible errors and content contracts |

## Scope boundaries

This core is not a POS, a payment processor or a WMS; it is the shared stock, rules and
store platform those systems integrate with.
