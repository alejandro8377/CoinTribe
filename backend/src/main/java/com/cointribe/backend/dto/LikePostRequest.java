package com.cointribe.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LikePostRequest(@NotNull @Positive Long userId) {
}
