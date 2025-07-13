package com.finemeet.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmTokenRequest(
        @NotBlank(message = "Token cannot be empty")
        String token
) {
}
