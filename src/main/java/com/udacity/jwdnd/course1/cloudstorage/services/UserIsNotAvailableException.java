package com.udacity.jwdnd.course1.cloudstorage.services;

public class UserIsNotAvailableException extends Exception {
    public UserIsNotAvailableException(String message) {
        super(message);
    }
}
