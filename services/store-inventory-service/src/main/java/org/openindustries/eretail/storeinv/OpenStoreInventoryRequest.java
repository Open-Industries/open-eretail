package org.openindustries.eretail.storeinv;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenStoreInventoryRequest(
        @NotBlank String sku,
        @PositiveOrZero long availableUnits) {
}
