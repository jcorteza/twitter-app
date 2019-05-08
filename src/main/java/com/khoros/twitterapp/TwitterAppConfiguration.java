package com.khoros.twitterapp;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import twitter4j.Twitter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TwitterAppConfiguration extends Configuration {
    @NotEmpty
    private int maxTweetLength;

    @Valid
    @NotNull
    private Twitter4JProperties twitter4jProps = new Twitter4JProperties();

    @JsonProperty
    public int getTweetLength() {
        return maxTweetLength;
    }

    @JsonProperty
    public Twitter4JProperties getTwitter4JProperties() {
        return twitter4jProps;
    }

    @JsonProperty("twitter4jProps")
    public void setTweetLength(int length) {
        maxTweetLength = length;
    }

    @JsonProperty("twitter4jProps")
    public void setTwitter4jProperties(Twitter4JProperties properties) {
        this.twitter4jProps = properties;
    }
}
