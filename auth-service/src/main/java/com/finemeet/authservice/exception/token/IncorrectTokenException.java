package com.finemeet.authservice.exception.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectTokenException extends RuntimeException {

    public IncorrectTokenException() {
        super("Incorrect token");
    }
}

