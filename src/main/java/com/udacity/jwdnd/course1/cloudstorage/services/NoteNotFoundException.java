package com.udacity.jwdnd.course1.cloudstorage.services;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
