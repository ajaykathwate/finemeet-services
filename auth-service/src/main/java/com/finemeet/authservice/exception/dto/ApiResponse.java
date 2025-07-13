package com.finemeet.authservice.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ApiResponse(
        @JsonProperty("message")
		String message,

		@JsonProperty("success")
		boolean success,

		@JsonProperty("httpStatusCode")
		Integer httpStatusCode,

		@JsonProperty("data")
		Object data
) {}

