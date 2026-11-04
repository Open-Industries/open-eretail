package org.openindustries.eretail.replenishment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "GROCERY|APPAREL|ELECTRONICS|PHARMACY") String category,
        @Positive int requiredMaxShrinkagePct,
        @Positive long requiredSafetyStockUnits) {
}
