package com.khoros.twitterapp.services;

public class TwitterServiceException extends Exception {

    public TwitterServiceException(String message) {

        super(message);

    }

    public TwitterServiceException (String message, Throwable cause) {

        super(message, cause);

    }

}
