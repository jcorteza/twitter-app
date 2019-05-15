package com.khoros.twitterapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class TwitterAppConfiguration extends Configuration {

    private Boolean debug;

    @Valid
    @NotNull
    private OauthFactory oauth = new OauthFactory();

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
}
