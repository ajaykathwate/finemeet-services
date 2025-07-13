package com.finemeet.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationResponse {
    private String token;
    private String refreshToken;
}
