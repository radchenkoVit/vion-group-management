package com.vion.backend.exceptions;

public class BrokenRequestException extends RuntimeException {
    public BrokenRequestException() {
    }

    public BrokenRequestException(String message) {
        super(message);
    }

    public BrokenRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
