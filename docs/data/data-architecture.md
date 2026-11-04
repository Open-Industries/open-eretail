# Data Architecture

Data view (VP-5) of the Architecture Description.

## 1. Ownership

| Data | Owner (only writer) | Store (reference → production) |
|---|---|---|
| Stores records | store-service | in-memory → PostgreSQL `store` |
| Store Inventory records | store-inventory-service | in-memory → PostgreSQL `storeinv` |
| Replenishment Rules records | replenishment-rules-service | in-memory → PostgreSQL `replenishment` |
| Promotions records | promotion-catalog-service | in-memory → PostgreSQL `promotion` |
| Notifications records | notification-service | in-memory → PostgreSQL `notification` |

In-memory adapters exist so the platform runs anywhere with zero setup; production
persistence is configuration (`DB_KIND`, `DB_URL`) plus the JPA adapter — the domain and
contracts never change (12-Factor IV).

## 2. Privacy and identifiers

Customer personal data is minimized and stays in adopter CRM systems; this core works with store, stock and promotion data. Card data never enters the platform (certified payment boundary). Internal keys are UUIDs. All values in this repository are synthetic.

## 3. Consistency, evidence, retention

Strong within a context; eventual across contexts via CloudEvents with idempotent
consumers. Consumption and decision records are append-only evidence; corrections are
compensating records, never edits. Retention per the adopter's regulatory schedule;
personal data erasable per data-subject rights while evidential records are retained in
pseudonymized form (UUIDs only). Backups per service, encrypted, restore tested
(ISO 27002 A.8.13).
