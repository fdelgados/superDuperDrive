package com.udacity.jwdnd.course1.cloudstorage.services;

public class UnableToUploadFileException extends RuntimeException {
    public UnableToUploadFileException(String message) {
        super(message);
    }
}
