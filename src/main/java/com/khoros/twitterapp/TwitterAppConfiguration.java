package com.khoros.twitterapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import twitter4j.conf.ConfigurationBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class TwitterAppConfiguration extends io.dropwizard.Configuration {

    @NotNull
    private int maxTweetLength;

    @NotNull
    private Boolean debug;

    @Valid
    @NotNull
    @JsonProperty
    private OauthFactory oauth = new OauthFactory();

    @JsonProperty
    public int getMaxTweetLength() {
        return maxTweetLength;
    }

    @JsonProperty
    public void setMaxTweetLength(int maxTweetLength) {
        this.maxTweetLength = maxTweetLength;
    }

    @JsonProperty
    public Boolean getDebug() {
        return debug;
    }

    @JsonProperty
    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    @JsonProperty
    public OauthFactory getOauthFactory() {
        return oauth;
    }

    @JsonProperty
    public void setOauthFactory(OauthFactory factory) {
        this.oauth = factory;
    }

    public twitter4j.conf.Configuration twitter4jConfigurationBuild() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(this.debug)
            .setOAuthConsumerKey(this.oauth.getConsumerKey())
            .setOAuthConsumerSecret(this.oauth.getConsumerSecret())
            .setOAuthAccessToken(this.oauth.getAccessToken())
            .setOAuthAccessTokenSecret(this.oauth.getAccessTokenSecret());
        return cb.build();
    }
}
