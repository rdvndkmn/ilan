package com.rdvn.ilan.dto;

public record CurrentUserResponse(
        String email,
        String role
) {
}