package com.finemeet.userservice.service;

import com.finemeet.userservice.dto.UserProfileResponseDto;
import com.finemeet.userservice.entity.UserProfile;
import com.finemeet.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserProfile {

    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;

    public UserProfileResponseDto getUserProfile(UUID userId) {
        log.info("Getting user profile for userId: {}", userId);
        UserProfile userProfile = userProfileRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User profile not found for userId: " + userId)
        );
        log.info("User profile found: {}", userProfile);

        return modelMapper.map(userProfile, UserProfileResponseDto.class);
    }

}
