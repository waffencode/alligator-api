package com.alligator.alligatorapi.exception;

public class PasswordDoesntMatchesException extends RuntimeException {
    public PasswordDoesntMatchesException() {
        super("Password mismatch");
    }
}
