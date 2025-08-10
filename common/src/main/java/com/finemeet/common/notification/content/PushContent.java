package com.finemeet.common.notification.content;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PushContent extends Content {

    @NotBlank
    private String deviceToken;

    @NotBlank
    private String pushTitle;

    @NotBlank
    private String pushBody;

    // Getters and setters
}

