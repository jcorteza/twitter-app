package com.khoros.twitterapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import twitter4j.conf.ConfigurationBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class TwitterAppConfiguration extends io.dropwizard.Configuration {

    @NotNull
    private Boolean debug;

    @Valid
    @NotNull
    @JsonProperty
    private TwitterAuthorization twitterAuthorization = new TwitterAuthorization();
    
    @JsonProperty
    public Boolean getDebug() {
        return debug;
    }

    @JsonProperty
    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    @JsonProperty
    public TwitterAuthorization getOauthFactory() {
        return twitterAuthorization;
    }

    @JsonProperty
    public void setOauthFactory(TwitterAuthorization factory) {
        this.twitterAuthorization = factory;
    }

    public twitter4j.conf.Configuration twitter4jConfigurationBuild() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(this.debug)
            .setOAuthConsumerKey(this.twitterAuthorization.getConsumerKey())
            .setOAuthConsumerSecret(this.twitterAuthorization.getConsumerSecret())
            .setOAuthAccessToken(this.twitterAuthorization.getAccessToken())
            .setOAuthAccessTokenSecret(this.twitterAuthorization.getAccessTokenSecret());
        return cb.build();
    }
}
