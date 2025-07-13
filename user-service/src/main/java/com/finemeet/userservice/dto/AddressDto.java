package com.finemeet.userservice.dto;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private UUID addressId;
    private String country;
    private String city;
    private String line;
    private String postcode;
}
