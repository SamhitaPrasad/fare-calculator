package com.littlepay.farecalculator.exception;

public class SimpleExceptionMessage {

    private String message;

    public SimpleExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
