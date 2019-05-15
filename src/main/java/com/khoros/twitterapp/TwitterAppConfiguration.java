package com.khoros.twitterapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class TwitterAppConfiguration extends Configuration {

    @Valid
    @NotNull
    private OauthFactory oauth = new OauthFactory();

    @JsonProperty
    public OauthFactory getOauthFactory() {
        return oauth;
    }

    @JsonProperty
    public void setOauthFactory(OauthFactory factory) {
        this.oauth = factory;
    }
}
