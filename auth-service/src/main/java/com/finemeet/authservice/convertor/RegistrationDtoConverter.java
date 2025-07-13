package com.finemeet.authservice.convertor;

import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.entity.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistrationDtoConverter {

    AuthUser toEntity(final UserRegistrationRequest userRegistrationRequest);
}

