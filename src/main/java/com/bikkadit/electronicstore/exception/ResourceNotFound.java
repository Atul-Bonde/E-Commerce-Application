package com.bikkadit.electronicstore.exception;

public class ResourceNotFound extends RuntimeException{

    String message;

    public ResourceNotFound(String message) {
        super(message);
        this.message = message;
    }
}
