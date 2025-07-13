package com.finemeet.userservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDate birthDate;
    private String phoneNumber;
    private AddressResponseDto address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

