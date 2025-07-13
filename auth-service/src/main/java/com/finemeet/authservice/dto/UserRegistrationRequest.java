package com.finemeet.authservice.dto;

import com.finemeet.authservice.validator.ValidPassword;
import com.finemeet.authservice.validator.ValidUsername;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UserRegistrationRequest {

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @ValidUsername
    private String username;

    @Pattern(
        regexp = "^[0-9]{10,15}$",
        message = "Phone must be between 10 to 15 digits"
    )
    @NotBlank(message = "Phone is required")
    private String phoneNumber;

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 16, message = "First name must be between 3 and 16 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 16, message = "Last name must be between 3 and 16 characters")
    private String lastName;

    @ValidPassword
    private String password;

    @NotNull
    @Past(message = "Birthdate must be in the past")
    private LocalDate birthDate;
}


