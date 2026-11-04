package org.openindustries.eretail.replenishment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReplenishmentRuleService {

    @Inject
    ReplenishmentRuleRepository repository;

    public ReplenishmentRule create(CreateReplenishmentRuleRequest request) {
        return repository.save(ReplenishmentRule.create(request.name(), request.category(),
                request.safetyStockUnits(), request.maxShrinkagePct(), request.priority()));
    }

    public ReplenishmentRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "ReplenishmentRule not found: " + id));
    }

    public List<ReplenishmentRule> list() {
        return repository.findAll();
    }

    public ReplenishmentRule activate(UUID id) {
        ReplenishmentRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.category());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.category());
        }
        for (ReplenishmentRule rule : candidates) {
            if (rule.maxShrinkagePct <= request.requiredMaxShrinkagePct()
                    && rule.safetyStockUnits >= request.requiredSafetyStockUnits()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.category() + " but none meets the requirement");
    }
}
