package com.finemeet.userservice.service;

import com.finemeet.userservice.dto.UserProfileRequestDto;
import com.finemeet.userservice.dto.UserProfileResponseDto;
import com.finemeet.userservice.entity.UserProfile;
import com.finemeet.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProfileService {

    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;

    public UserProfileResponseDto createUserProfile(final UserProfileRequestDto userProfileRequestDto){
        UserProfile userProfile = modelMapper.map(userProfileRequestDto, UserProfile.class);
        UserProfile savedUserProfile = userProfileRepository.save(userProfile);
        return modelMapper.map(savedUserProfile, UserProfileResponseDto.class);
    }

}
