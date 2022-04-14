package com.swed.carpark.exception;


public class UuidNotFoundException extends RuntimeException {
    public UuidNotFoundException() {
        super();
    }
    public UuidNotFoundException(String message) {
        super(message);
    }
}
