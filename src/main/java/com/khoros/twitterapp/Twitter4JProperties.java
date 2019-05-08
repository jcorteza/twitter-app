package com.khoros.twitterapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
//import twitter4j.auth.

public class Twitter4JProperties {
    @NotEmpty
    private String debug;

    @NotEmpty
    private String consumerKey;

    @NotEmpty
    private String consumerSecret;

    @NotEmpty
    private String accessToken;

    @NotEmpty
    private String accessTokenSecret;

    @JsonProperty
    public String getDebug() {
        return debug;
    }

    @JsonProperty
    public String getConsumerKey() {
        return consumerKey;
    }

    @JsonProperty
    public String getConsumerSecret() {
        return consumerSecret;
    }

    @JsonProperty
    public String getAccessToken() {
        return accessToken;
    }

    @JsonProperty
    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    @JsonProperty
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    @JsonProperty
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    @JsonProperty
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonProperty
    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }
}
