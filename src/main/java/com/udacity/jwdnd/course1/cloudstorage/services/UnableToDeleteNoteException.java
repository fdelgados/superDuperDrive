package com.udacity.jwdnd.course1.cloudstorage.services;

public class UnableToDeleteNoteException extends RuntimeException {
    public UnableToDeleteNoteException(String message) {
        super(message);
    }
}
