package com.cs.sigm.exception;

public class EntryNotFoundException extends RuntimeException {

    public EntryNotFoundException() {
        super("Entry not found.");
    }

    public EntryNotFoundException(String message) {
        super(message);
    }

}
