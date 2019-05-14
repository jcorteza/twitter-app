package com.khoros.twitterapp;

import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;

import java.util.ArrayList;

public class ResponseImplTest<T> extends ArrayList<T> implements ResponseList<T> {
    private RateLimitStatus rateLimitStatus = null;
    private int accessLevel = 3;

    public RateLimitStatus getRateLimitStatus() {
        return this.rateLimitStatus;
    }

    public int getAccessLevel() {
        return this.accessLevel;
    }
}