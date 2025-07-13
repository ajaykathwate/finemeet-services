package com.finemeet.authservice.exception.token;

public class IncorrectTokenFormatException extends RuntimeException{

    public IncorrectTokenFormatException(final String tokenFormat) {
        super("Incorrect token format, token must be " + tokenFormat);
    }
}

