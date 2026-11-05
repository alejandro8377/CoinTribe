package com.cointribe.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChallengeJoinRequest(@NotNull @Positive Long userId) {
}
