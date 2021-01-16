package com.udacity.jwdnd.course1.cloudstorage.services;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }
}
