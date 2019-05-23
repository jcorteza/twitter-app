package com.khoros.twitterapp;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

import java.util.Date;

public class Twitter4jStatusImpl implements Status {

    public Date getCreatedAt() {
        return new Date(1557851237000l);
    };

    public long getId() {
        return 9999999999l;
    };

    public String getText() {
        return "Tweet Text";
    };

    public int getDisplayTextRangeStart() {
        return 0;
    };

    public int getDisplayTextRangeEnd() {
        return 0;
    };

    public String getSource() {
        return null;
    };

    public boolean isTruncated() {
        return false;
    };

    public long getInReplyToStatusId() {
        return -1l;
    };

    public long getInReplyToUserId() {
        return -1l;
    };

    public String getInReplyToScreenName() {
        return null;
    };

    public GeoLocation getGeoLocation() {
        return null;
    };

    public Place getPlace() {
        return null;
    };

    public boolean isFavorited() {
        return false;
    };

    public boolean isRetweeted() {
        return false;
    };

    public int getFavoriteCount() {
        return 0;
    };

    public User getUser() {
        return new Twitter4jUserImpl();
    };

    public boolean isRetweet() {
        return false;
    };

    public Status getRetweetedStatus() {
        return null;
    };

    public long[] getContributors() {
        return null;
    };

    public int getRetweetCount() {
        return 0;
    };

    public boolean isRetweetedByMe() {
        return false;
    };

    public long getCurrentUserRetweetId() {
        return -1l;
    };

    public boolean isPossiblySensitive() {
        return false;
    };

    public String getLang() {
        return "en";
    };

    public Scopes getScopes() {
        return null;
    };

    public String[] getWithheldInCountries() {
        return null;
    };

    public long getQuotedStatusId() {
        return -1l;
    };

    public Status getQuotedStatus() {
        return null;
    };

    public URLEntity getQuotedStatusPermalink() {
        return null;
    };

    public int compareTo(Status status) {
        return 0;
    }

    public RateLimitStatus getRateLimitStatus() {
        return null;
    }

    public int getAccessLevel() {
        return 1;
    }

    public UserMentionEntity[] getUserMentionEntities() {
        return null;
    };

    public URLEntity[] getURLEntities() {
        return null;
    };

    public HashtagEntity[] getHashtagEntities() {
        return null;
    };

    public MediaEntity[] getMediaEntities() {
        return null;
    };

    public SymbolEntity[] getSymbolEntities() {
        return null;
    };
}
