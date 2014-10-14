package com.pitshop;

public class PitshopDbConnectionException extends Exception {
    private int errorCode;
    public PitshopDbConnectionException(String message) {
        super(message);
    }

    public PitshopDbConnectionException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
