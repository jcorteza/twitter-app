package com.khoros.twitterapp;

import java.lang.Throwable;

public class LengthException extends Exception {
    private String message = "Your text length: ";
    private Throwable cause = new Throwable("Tweet text surpassed 280 characters.");

    public LengthException(int characters) {
        message += characters;
    }

    public String getExceptionMessage() {
        return message;
    }

    public String getCauseMessage() {
        return cause.getMessage();
    }
}
