package com.finemeet.authservice.kafka.dto.content;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailContent extends Content {

    @NotBlank
    private String templateId;

    @NotBlank
    private String subject;

    @NotNull
    private Map<String, Object> templateData;

    // Getters and setters
}

