package com.khoros.twitterapp;

import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;

import java.util.Date;

public class Twitter4jUserImpl implements User {

    public long getId() {
        return 9999999999l;
    }

    public String getName() {
        return "Twitter User";
    }

    public String getEmail() {
        return null;
    }

    public String getScreenName() {
        return "twitterUser";
    }

    public String getLocation() {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public boolean isContributorsEnabled() {
        return false;
    }

    public String getProfileImageURL() {
        return "http://www.google.com/image";
    }

    public String getBiggerProfileImageURL() {
        return null;
    }

    public String getMiniProfileImageURL() {
        return null;
    }

    public String getOriginalProfileImageURL() {
        return null;
    }

    public String get400x400ProfileImageURL() {
        return null;
    }

    public String getProfileImageURLHttps() {
        return null;
    }

    public String getBiggerProfileImageURLHttps() {
        return null;
    }

    public String getMiniProfileImageURLHttps() {
        return null;
    }

    public String getOriginalProfileImageURLHttps() {
        return null;
    }

    public String get400x400ProfileImageURLHttps() {
        return null;
    }

    public boolean isDefaultProfileImage() {
        return true;
    }

    public String getURL() {
        return null;
    }

    public boolean isProtected() {
        return false;
    }

    public int getFollowersCount() {
        return 0;
    }

    public Status getStatus() {
        return null;
    }

    public String getProfileBackgroundColor() {
        return null;
    }

    public String getProfileTextColor() {
        return null;
    }

    public String getProfileLinkColor() {
        return null;
    }

    public String getProfileSidebarFillColor() {
        return null;
    }

    public String getProfileSidebarBorderColor() {
        return null;
    }

    public boolean isProfileUseBackgroundImage() {
        return false;
    }

    public boolean isDefaultProfile() {
        return false;
    }

    public boolean isShowAllInlineMedia() {
        return false;
    }

    public int getFriendsCount() {
        return 0;
    }

    public Date getCreatedAt() {
        return new Date(1557851237000l);
    }

    public int getFavouritesCount() {
        return 0;
    }

    public int getUtcOffset() {
        return 0;
    }

    public String getTimeZone() {
        return null;
    }

    public String getProfileBackgroundImageURL() {
        return null;
    }

    public String getProfileBackgroundImageUrlHttps() {
        return null;
    }

    public String getProfileBannerURL() {
        return null;
    }

    public String getProfileBannerRetinaURL() {
        return null;
    }

    public String getProfileBannerIPadURL() {
        return null;
    }

    public String getProfileBannerIPadRetinaURL() {
        return null;
    }

    public String getProfileBannerMobileURL() {
        return null;
    }

    public String getProfileBannerMobileRetinaURL() {
        return null;
    }

    public String getProfileBanner300x100URL() {
        return null;
    }

    public String getProfileBanner600x200URL() {
        return null;
    }

    public String getProfileBanner1500x500URL() {
        return null;
    }

    public boolean isProfileBackgroundTiled() {
        return false;
    };

    public String getLang() {
        return null;
    }

    public int getStatusesCount() {
        return 0;
    }

    public boolean isGeoEnabled() {
        return false;
    }

    public boolean isVerified() {
        return false;
    }

    public boolean isTranslator() {
        return false;
    }

    public int getListedCount() {
        return 0;
    }

    public boolean isFollowRequestSent() {
        return false;
    }

    public URLEntity[] getDescriptionURLEntities() {
        return null;
    }

    public URLEntity getURLEntity() {
        return null;
    }

    public String[] getWithheldInCountries() {
        return null;
    }

    public int compareTo(User user) {
        return 0;
    }

    public RateLimitStatus getRateLimitStatus() {
        return null;
    }

    public int getAccessLevel() {
        return 1;
    }

}
