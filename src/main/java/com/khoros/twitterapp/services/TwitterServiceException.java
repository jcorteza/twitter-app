package com.khoros.twitterapp.services;

public class TwitterServiceException extends Exception {

    public String message;
    public Throwable cause;

    public TwitterServiceException(String message) {

        this.message = message;

    }

    public TwitterServiceException (String message, Throwable cause) {

        this.message = message;
        this.cause = cause;

    }

    @Override
    public String getMessage() {

        return message;

    }

    @Override
    public Throwable getCause() {

        return cause;

    }

}
