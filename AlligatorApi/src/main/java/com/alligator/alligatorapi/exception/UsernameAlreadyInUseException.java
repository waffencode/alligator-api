package com.alligator.alligatorapi.exception;

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException(String username) {
        super("Username " + username + " already exists");
    }
}