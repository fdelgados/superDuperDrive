package com.udacity.jwdnd.course1.cloudstorage.services;

public class UnableToSaveNoteException extends RuntimeException {
    public UnableToSaveNoteException(String message) {
        super(message);
    }
}
