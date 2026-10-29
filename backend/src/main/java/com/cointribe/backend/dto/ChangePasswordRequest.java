package com.cointribe.backend.dto;

public record ChangePasswordRequest(String currentPassword, String newPassword) {
}
