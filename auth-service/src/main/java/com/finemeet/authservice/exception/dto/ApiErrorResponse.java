package com.finemeet.authservice.exception.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ApiErrorResponse(

        @JsonProperty("message")
        String message,

        @JsonProperty("httpStatusCode")
        Integer httpStatusCode,

        @JsonProperty("success")
	    boolean success,

        @JsonProperty("errors")
	    Object errors
) {}

