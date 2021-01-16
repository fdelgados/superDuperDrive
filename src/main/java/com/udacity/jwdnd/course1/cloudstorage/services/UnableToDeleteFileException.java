package com.udacity.jwdnd.course1.cloudstorage.services;

public class UnableToDeleteFileException extends Exception {
    public UnableToDeleteFileException(String message) {
        super(message);
    }
}
