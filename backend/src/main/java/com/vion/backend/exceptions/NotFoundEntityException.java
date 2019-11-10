package com.vion.backend.exceptions;

public class NotFoundEntityException extends RuntimeException {

    public NotFoundEntityException() {
    }

    public NotFoundEntityException(String message) {
        super(message);
    }

    public NotFoundEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
