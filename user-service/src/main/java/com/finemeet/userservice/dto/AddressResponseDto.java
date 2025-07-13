package com.finemeet.userservice.dto;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
    private UUID addressId;
    private String street;
    private String city;
    private String state;
    private String postcode;
    private String country;
}
