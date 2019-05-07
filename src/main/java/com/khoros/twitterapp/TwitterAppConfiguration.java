package com.khoros.twitterapp;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class TwitterAppConfiguration extends Configuration {
    @NotEmpty
    private int tweetLength;

    @NotEmpty
    private String tweetText;

    @JsonProperty
    public int getTweetLength() {
        return tweetLength;
    }

    @JsonProperty
    public String getTweetText() {
        return tweetText;
    }

    @JsonProperty
    public void setTweetLength(int length) {
        tweetLength = length;
    }
    @JsonProperty
    public void setTweetText(String text) {
        tweetText = text;
    }
}
