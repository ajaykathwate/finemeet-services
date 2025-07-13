package com.finemeet.authservice.convertor;

import com.finemeet.authservice.dto.AuthUserDto;
import com.finemeet.authservice.entity.AuthUser;
import org.springframework.stereotype.Component;

@Component
public class AuthUserDtoConverter {

    public AuthUserDto toDto(AuthUser authUser) {
        return AuthUserDto.builder()
                .id(authUser.getId())
                .email(authUser.getEmail())
                .isEmailVerified(authUser.isEmailVerified())
                .enabled(authUser.isEnabled())
                .build();
    }
}

