package com.finemeet.userservice.endpoint;

import com.finemeet.userservice.dto.UserProfileRequestDto;
import com.finemeet.userservice.dto.UserProfileResponseDto;
import com.finemeet.userservice.service.CreateProfileService;
import com.finemeet.userservice.service.GetUserProfile;
import jakarta.validation.Valid;
import java.util.UUID;
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
    private final GetUserProfile getUserProfileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserProfileResponseDto> createUserProfile(@RequestBody @Valid UserProfileRequestDto userProfileRequestDto) {
        UserProfileResponseDto response = createProfileService.createUserProfile(userProfileRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserProfileResponseDto> getUserProfile(@PathVariable UUID userId) {
        UserProfileResponseDto response = getUserProfileService.getUserProfile(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(){
        return "User Service is running!";
    }

}
