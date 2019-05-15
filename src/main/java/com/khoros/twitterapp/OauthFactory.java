package com.khoros.twitterapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;

public class OauthFactory {
    @NotBlank
    private CharSequence consumerKey;

    @NotBlank
    private CharSequence consumerSecret;

    @NotBlank
    private CharSequence accessToken;

    @NotBlank
    private CharSequence accessTokenSecret;

    @JsonProperty
    public String getConsumerKey() {
        return consumerKey.toString();
    }

    @JsonProperty
    public String getConsumerSecret() {
        return consumerSecret.toString();
    }

    @JsonProperty
    public String getAccessToken() {
        return accessToken.toString();
    }

    @JsonProperty
    public String getAccessTokenSecret() {
        return accessTokenSecret.toString();
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
