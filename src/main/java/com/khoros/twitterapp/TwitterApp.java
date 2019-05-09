package com.khoros.twitterapp;

import sun.security.util.Length;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;

public class TwitterApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter either \"tweet\" or \"check_feed\" as the first argument in your command.");
            System.exit(-1);
        }
        try {
            Twitter factory = new TwitterFactory().getSingleton();
            switch (args[0].toLowerCase()) {
                case "tweet":
                    try {
                        String statusText = "";

                        if (args.length > 1) {
                            StringBuilder tweetBuilder = new StringBuilder(statusText);
                            for (int i = 1; i < args.length; i++) {
                                tweetBuilder.append(" " + args[i]);
                            }
                            statusText = tweetBuilder.toString();
                        } else {
                            throw new LengthException(statusText.length());
                        }
                        if (statusText.length() > 280 || statusText.length() == 0) {
                            throw new LengthException(statusText.length());
                        }
                        Status newStatus = factory.updateStatus(statusText);
                        System.out.println("Status was successfully updated to \"" + statusText + "\"");
                        System.exit(0);
                    } catch (LengthException lengthException) {
                        System.out.println(lengthException.getExceptionMessage());
                        System.out.println(lengthException.getCauseMessage());
                        System.exit(-1);
                    } catch (TwitterException tweetException) {
                        tweetException.printStackTrace();
                        System.out.println(tweetException.getErrorMessage());
                        System.exit(-1);
                    }
                    break;
                case "check_feed":
                    try {
                        String lineBreak = "========================================================================";
                        List<Status> tweetsFeed = factory.getHomeTimeline();
                        System.out.println("Showing home timeline.");
                        System.out.println(lineBreak);
                        for (Status tweet : tweetsFeed) {
                            System.out.println(tweet.getUser().getName() + "\n" + tweet.getText() + "\n" + lineBreak);
                        }
                        System.exit(0);
                    } catch (TwitterException feedException) {
                        feedException.printStackTrace();
                        System.out.println(feedException.getErrorMessage());
                        System.exit(-1);
                    }
                    break;
                default:
                    throw new CommandNotFoundException(args[0]);
            }
        } catch(CommandNotFoundException commandNotFound) {
            System.out.println(commandNotFound.getCause());
            System.out.println(commandNotFound.getMessage());
            System.exit(-1);
        }
    }
}
