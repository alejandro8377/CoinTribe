package com.cointribe.backend.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @Size(max = 255) String nombre,
        @Positive Double meta
) {
}
