package com.finemeet.authservice.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthUserDto {
    private UUID id;
    private String email;
    private boolean isEmailVerified;
    private boolean enabled;
}