package com.khoros.twitterapp;

import java.lang.Throwable;

public class LengthException extends Exception {
    private String message = "Your text length: ";
    private Throwable cause;

    public LengthException(int characters) {
        message += characters;
        if(characters > 280) {
            cause = new Throwable("Error: Tweet text surpassed 280 characters.");
        } else if (characters == 0) {
            cause = new Throwable("Error: No tweet text entered.");
        }
    }

    public String getExceptionMessage() {
        return message;
    }

    public String getCauseMessage() {
        return cause.getMessage();
    }
}
