package com.cointribe.backend.dto;

import com.cointribe.backend.models.UserAchievement;

import java.time.LocalDateTime;

public record UserAchievementResponseDto(
        Long id,
        Long userId,
        Long achievementId,
        LocalDateTime fechaDesbloqueo
) {

    public static UserAchievementResponseDto from(UserAchievement userAchievement) {
        return new UserAchievementResponseDto(
                userAchievement.getId(),
                userAchievement.getUser().getId(),
                userAchievement.getAchievement().getId(),
                userAchievement.getFechaDesbloqueo()
        );
    }
}
