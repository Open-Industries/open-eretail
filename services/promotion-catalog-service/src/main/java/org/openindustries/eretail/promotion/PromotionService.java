package org.openindustries.eretail.promotion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PromotionService {

    @Inject
    PromotionRepository repository;

    public Promotion create(CreatePromotionRequest request) {
        return repository.save(Promotion.create(request.name(), request.promoType(), request.discountPct()));
    }

    public Promotion get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Promotion not found: " + id));
    }

    public List<Promotion> list() {
        return repository.findAll();
    }

    public Promotion activate(UUID id) {
        Promotion entity = get(id);
        if (!"DRAFT".equals(entity.status)) {
            throw new DomainException(409, "Only a DRAFT promotion can transition via activate");
        }
        entity.status = "ACTIVE";
        return repository.save(entity);
    }
}
