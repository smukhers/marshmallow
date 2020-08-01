package com.sanjeet.marshmallow.error;

public class ParameterParsingException extends IllegalArgumentException {
    public ParameterParsingException(String message) {
        super(message);
    }
}
