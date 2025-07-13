package com.finemeet.authservice.exception.dto;

import jakarta.annotation.Nullable;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ApiResponseCreator {
    public ApiResponse buildResponse(String successMessage, boolean success, HttpStatus httpStatus, @Nullable Object data) {
        return ApiResponse.builder()
                .message(successMessage)
                .success(success)
                .httpStatusCode(httpStatus.value())
                .data(data)
                .build();
    }

    // Overload without errors
    public ApiResponse buildResponse(String successMessage, boolean success, HttpStatus httpStatus) {
        return buildResponse(successMessage, success, httpStatus, Collections.emptyMap());
    }
}
