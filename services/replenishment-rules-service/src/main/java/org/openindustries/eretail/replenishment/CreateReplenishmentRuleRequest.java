package org.openindustries.eretail.replenishment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateReplenishmentRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "GROCERY|APPAREL|ELECTRONICS|PHARMACY") String category,
        @Positive long safetyStockUnits,
        @Positive int maxShrinkagePct,
        @Min(1) @Max(100) int priority) {
}
