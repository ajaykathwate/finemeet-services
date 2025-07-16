package com.finemeet.authservice.exception.handler;

import com.finemeet.authservice.exception.dto.ApiErrorResponse;
import com.finemeet.authservice.exception.dto.ApiErrorResponseCreator;
import com.finemeet.authservice.exception.dto.ErrorDebugMessageCreator;
import com.finemeet.authservice.exception.token.IncorrectTokenException;
import com.finemeet.authservice.exception.token.IncorrectTokenFormatException;
import com.finemeet.authservice.exception.token.TemplateBuilderNotFoundException;
import com.finemeet.authservice.exception.token.TimeTokenException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class TokenExceptionHandler {
    private final ApiErrorResponseCreator apiErrorResponseCreator;
    private final ErrorDebugMessageCreator errorDebugMessageCreator;

    @ExceptionHandler(TemplateBuilderNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleMessageBuilderNotFoundException(final TemplateBuilderNotFoundException exception) {

        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("builder", "No template builder found for type: " + exception.getMessage());

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Email template configuration error",
            false,
            HttpStatus.INTERNAL_SERVER_ERROR,
            errors
        );

        log.error(
            "Handle message builder not found exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    @ExceptionHandler(IncorrectTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIncorrectTokenException(final IncorrectTokenException exception) {

        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("token", "Invalid or expired token: " + exception.getMessage());

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Token validation failed",
            false,
            HttpStatus.BAD_REQUEST,
            errors
        );

        log.error(
            "Handle incorrect token exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    @ExceptionHandler(IncorrectTokenFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIncorrectTokenFormatException(final IncorrectTokenFormatException exception) {

        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("token", "Token format is incorrect: " + exception.getMessage());

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Token format validation failed",
            false,
            HttpStatus.BAD_REQUEST,
            errors
        );

        log.error(
            "Handle incorrect token format exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    @ExceptionHandler(TimeTokenException.class)
    @ResponseStatus(HttpStatus.TOO_EARLY)
    public ApiErrorResponse handleTimeTokenException(final TimeTokenException exception) {

        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("token", exception.getMessage());

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Token timing validation failed",
            false,
            HttpStatus.TOO_EARLY,
            errors
        );

        log.error(
            "Handle time token exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

}

