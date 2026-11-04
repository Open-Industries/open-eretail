package org.openindustries.eretail.promotion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreatePromotionRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "DISCOUNT|BUNDLE|LOYALTY") String promoType,
        @Positive int discountPct) {
}
