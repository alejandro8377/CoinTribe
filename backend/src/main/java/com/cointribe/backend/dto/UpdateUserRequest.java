package com.cointribe.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(max = 255) String nombre,
        @Email @Size(max = 255) String email,
        @Positive Double meta,
        @Positive Integer nivel
) {
}
