package com.cointribe.backend.dto;

public record UpdateUserRequest(String nombre, String email, Double meta, Integer nivel) {
}
