package com.cointribe.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CoinRequest(
        @NotBlank(groups = ValidationGroups.Create.class) @Size(max = 255) String name,
        @NotBlank(groups = ValidationGroups.Create.class) @Size(max = 50) String symbol,
        @PositiveOrZero Double price
) {
}
