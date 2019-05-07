package com.khoros.twitterapp;

public class CommandNotFoundException extends Exception {
    CommandNotFoundException(String command) {
        super("Please enter 'tweet' or 'check_feed' as your first command line argument.", new Throwable("Error: Command '" + command + "' not found."));
    }
}
