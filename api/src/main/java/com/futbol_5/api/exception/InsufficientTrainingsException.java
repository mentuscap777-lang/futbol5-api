package com.futbol_5.api.exception;

public class InsufficientTrainingsException extends RuntimeException {
    public InsufficientTrainingsException(String message) {
        super(message);
    }
}