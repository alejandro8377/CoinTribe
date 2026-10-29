package com.cointribe.backend.dto;

import com.cointribe.backend.models.UserChallenge;

import java.time.LocalDateTime;

public record UserChallengeResponseDto(
        Long id,
        Long userId,
        Long challengeId,
        Double ahorroAcumulado,
        LocalDateTime fechaUnion,
        Boolean completado
) {

    public static UserChallengeResponseDto from(UserChallenge userChallenge) {
        return new UserChallengeResponseDto(
                userChallenge.getId(),
                userChallenge.getUser().getId(),
                userChallenge.getChallenge().getId(),
                userChallenge.getAhorroAcumulado(),
                userChallenge.getFechaUnion(),
                userChallenge.getCompletado()
        );
    }
}
