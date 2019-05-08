package com.khoros.twitterapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class TweetText {

    @Length(min = 1, max = 280)
    private String tweetText;

    public TweetText() {
        // Jackson deserialization?
    }

    public TweetText(String message) {
        tweetText = message;
    }

    @JsonProperty
    public String getTweetText() {
        return tweetText;
    }

    @JsonProperty
    public void setTweetText(String message) {
        tweetText = message;
    }
}
