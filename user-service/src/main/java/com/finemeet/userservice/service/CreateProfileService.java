package com.finemeet.userservice.service;

import com.finemeet.userservice.dto.UserProfileRequestDto;
import com.finemeet.userservice.dto.UserProfileResponseDto;
import com.finemeet.userservice.entity.UserProfile;
import com.finemeet.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateProfileService {

    private final UserProfileRepository userProfileRepository;

    private final ModelMapper modelMapper;

    public UserProfileResponseDto createUserProfile(final UserProfileRequestDto userProfileRequestDto){
        log.info("Creating user profile with request: {}", userProfileRequestDto);

        UserProfile userProfile = modelMapper.map(userProfileRequestDto, UserProfile.class);
        log.debug("Mapped UserProfile entity (pre-save): {}", userProfile);

        UserProfile savedUserProfile = userProfileRepository.save(userProfile);
        log.info("User profile saved successfully with ID: {}", savedUserProfile.getId());
        log.debug("Saved UserProfile entity: {}", savedUserProfile);

        return modelMapper.map(savedUserProfile, UserProfileResponseDto.class);
    }

}
