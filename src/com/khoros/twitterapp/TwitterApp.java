package com.khoros.twitterapp;

import twitter4j.auth.AccessToken;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;

public class TwitterApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter either \"tweet\" or \"check_feed\" as a command.");
            System.exit(-1);
        }
        Twitter factory = new TwitterFactory().getSingleton();
        switch (args[0].toLowerCase()) {
            case "tweet":
                String statusText;

                if (args.length == 1) {
                    statusText = "Hello Twitter followers!";
                } else {
                    statusText = args[1];
                }
                try {
                    Status newStatus = factory.updateStatus(statusText);
                    System.out.println("Status was successfully updated to \"" + statusText + ".\"");
                    System.exit(0);
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
                    for(Status tweet : tweetsFeed) {
                        System.out.println(tweet.getUser().getName() + "\n" + tweet.getText() + "\n" + lineBreak);
                    }
                    System.exit(0);
                } catch(TwitterException feedException) {
                    feedException.printStackTrace();
                    System.out.println(feedException.getErrorMessage());
                    System.exit(-1);
                }
                break;
        }
    }

}
