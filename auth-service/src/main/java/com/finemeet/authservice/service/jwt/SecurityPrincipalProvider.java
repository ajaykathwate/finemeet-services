package com.finemeet.authservice.service.jwt;

import com.finemeet.authservice.convertor.AuthUserDtoConverter;
import com.finemeet.authservice.dto.AuthUserDto;
import com.finemeet.authservice.entity.AuthUser;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityPrincipalProvider {

    private final AuthUserDtoConverter userDtoConverter;

    public AuthUserDto get() {
        AuthUser userEntity = (AuthUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return userDtoConverter.toDto(userEntity);
    }

    public UUID getUserId() {
        return get().getId();
    }

}
