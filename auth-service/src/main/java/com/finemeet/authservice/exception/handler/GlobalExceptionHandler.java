package com.finemeet.authservice.exception.handler;
import com.finemeet.authservice.exception.FieldAlreadyExistsException;
import com.finemeet.authservice.exception.ResourceAlreadyExistsException;
import com.finemeet.authservice.exception.ResourceNotFoundException;
import com.finemeet.authservice.exception.dto.ApiErrorResponse;
import com.finemeet.authservice.exception.dto.ApiErrorResponseCreator;
import com.finemeet.authservice.exception.dto.ErrorDebugMessageCreator;
import jakarta.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ApiErrorResponseCreator apiErrorResponseCreator;
    private final ErrorDebugMessageCreator errorDebugMessageCreator;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception){
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Validation Failed",
            false,
            HttpStatus.BAD_REQUEST,
            errors
        );

        log.error(
            "Handle method argument not valid exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleConstraintViolationException(final ConstraintViolationException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        exception.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Validation Failed",
            false,
            HttpStatus.BAD_REQUEST,
            errors
        );

        log.error(
            "Handle constraint violation exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    @ExceptionHandler({
        ResourceNotFoundException.class,
        NoSuchElementException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleResourceNotFoundException(final ResourceNotFoundException exception) {
        String errorMessage = exception.getMessage() != null ?
                         exception.getMessage() :
                         "The requested resource was not found";

        Map<String, String> errors = new LinkedHashMap<>();
        errors.put("resource", errorMessage);

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Resource Not Found",
            false,
            HttpStatus.NOT_FOUND,
            errors
        );

        log.warn(
            "Handle resource not found exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    @ExceptionHandler(FieldAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleFieldAlreadyExistsException(final FieldAlreadyExistsException exception) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.put(exception.getResourceName().toLowerCase(), exception.getMessage());

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Field already exists",
            false,
            HttpStatus.CONFLICT,
            errors
        );

        log.warn(
            "Handle field already exists exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    // Handle Database Errors (500)
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleDataAccessException(final DataAccessException exception) {
        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Database operation failed",
            false,
            HttpStatus.INTERNAL_SERVER_ERROR
        );

        log.error(
            "Handle database exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    // Handle security-related exceptions (403)
    @ExceptionHandler({
        AccessDeniedException.class,
        DisabledException.class,
        LockedException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleSecurityException(final Exception exception) {
        String message = switch (exception.getClass().getSimpleName()) {
            case "DisabledException" -> "Your account is disabled. Please contact support";
            case "LockedException" -> "Account temporarily locked due to multiple failed attempts";
            default -> "Access to this resource is restricted";
        };

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            message,
            false,
            HttpStatus.FORBIDDEN
        );

        log.error(
            "Handle security exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    // Handle authentication failures (401)
    @ExceptionHandler({
        BadCredentialsException.class,
        UsernameNotFoundException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleAuthenticationException(final Exception exception) {
        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
            "Bad Credentials",
            false,
            HttpStatus.UNAUTHORIZED
        );

        log.error(
            "Handle authentication exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception) {
        String errorMessage = exception.getMessage() != null
                ? exception.getMessage()
                : "The resource already exists";

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
                errorMessage,
                false,
                HttpStatus.CONFLICT
        );

        log.warn(
            "Handle resource already exists exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        return apiErrorResponse;
    }

}

