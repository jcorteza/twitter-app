package com.khoros.twitterapp.services;

public class TwitterServiceException extends Exception {

    TwitterServiceException(String message) {

        super(message);

    }

    TwitterServiceException (String message, Throwable cause) {

        super(message, cause);

    }

}
