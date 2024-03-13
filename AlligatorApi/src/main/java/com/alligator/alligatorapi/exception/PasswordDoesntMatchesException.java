package com.alligator.alligatorapi.exception;

public class PasswordDoesntMatchesException extends RuntimeException {
    public PasswordDoesntMatchesException(String message) {
        super(message);
    }
}
