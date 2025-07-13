package com.finemeet.userservice.endpoint;

import com.finemeet.userservice.dto.UserProfileRequestDto;
import com.finemeet.userservice.dto.UserProfileResponseDto;
import com.finemeet.userservice.service.CreateProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = UserProfileEndpoint.USER_SERVICE_ENDPOINT)
public class UserProfileEndpoint {
    public static final String USER_SERVICE_ENDPOINT = "/api/users";

    private final CreateProfileService createProfileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserProfileResponseDto> createUserProfile(@RequestBody @Valid UserProfileRequestDto userProfileRequestDto) {
        UserProfileResponseDto response = createProfileService.createUserProfile(userProfileRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
