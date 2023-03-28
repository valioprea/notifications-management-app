package com.vali.notifications.exceptions;

public class CustomDatabaseException extends RuntimeException {

    private final String message;

    public CustomDatabaseException(String message) {
        this.message = message;
    }
}
