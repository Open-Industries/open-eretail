package org.openindustries.eretail.storeinv;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateStoreReservationRequest(
        @NotBlank @Pattern(regexp = "POS|CLICK_COLLECT|DELIVERY") String channel,
        @Positive long units) {
}
