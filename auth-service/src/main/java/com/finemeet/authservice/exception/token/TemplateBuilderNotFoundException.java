package com.finemeet.authservice.exception.token;

import lombok.Getter;

@Getter
public class TemplateBuilderNotFoundException extends RuntimeException {

    private final String className;

    public TemplateBuilderNotFoundException(String className) {
        super("No template builder found for " + className);
        this.className = className;
    }
}

