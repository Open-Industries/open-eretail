package org.openindustries.eretail.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateStoreRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "FLAGSHIP|STANDARD|EXPRESS|DARK_STORE") String format,
        @NotBlank String regionCode) {
}
