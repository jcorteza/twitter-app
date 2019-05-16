package com.khoros.twitterapp;

import static org.mockito.Mockito.*;

import com.khoros.twitterapp.resources.StatusUpdateResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.lang.Exception;
import java.net.HttpURLConnection;
import org.apache.commons.lang3.StringUtils;

public class StatusUpdateResourceTest {

    @InjectMocks
    private StatusUpdateResource statusResource;

    @Mock
    private Twitter mockFactory;

    private String exampleText;
    private String exceptionEntity;
    private Status statusEntity;

    @Before
    public void setup() {
        mockFactory = mock(Twitter.class);
        statusResource = new StatusUpdateResource(mockFactory);
    }

    @After
    public void resetMock() {
        reset(mockFactory);
    }

    @Test
    public void statusUpdateTest() {
        exampleText = "Tweet";

        try {
            statusEntity = TwitterObjectFactory.createStatus("{\"rateLimitStatus\":null,\"accessLevel\":2,\"createdAt\":1557851237000,\"id\":1128335988466622465,\"text\":\"Taco Tuesday\",\"displayTextRangeStart\":0,\"displayTextRangeEnd\":12,\"source\":\"<a href=\\\"https://github.com/jcorteza\\\" rel=\\\"nofollow\\\">Tweeting—Khoros Lab C104</a>\",\"inReplyToStatusId\":-1,\"inReplyToUserId\":-1,\"favoriteCount\":0,\"inReplyToScreenName\":null,\"geoLocation\":null,\"place\":null,\"retweetCount\":0,\"lang\":\"en\",\"retweetedStatus\":null,\"userMentionEntities\":[],\"hashtagEntities\":[],\"mediaEntities\":[],\"symbolEntities\":[],\"currentUserRetweetId\":-1,\"scopes\":null,\"user\":{\"rateLimitStatus\":null,\"accessLevel\":0,\"id\":2422687974,\"name\":\"Josephine Cortez\",\"email\":null,\"screenName\":\"joC346\",\"location\":\"\",\"description\":\"\",\"descriptionURLEntities\":[],\"url\":null,\"followersCount\":41,\"status\":null,\"profileBackgroundColor\":\"ACDED6\",\"profileTextColor\":\"333333\",\"profileLinkColor\":\"94D487\",\"profileSidebarFillColor\":\"DDEEF6\",\"profileSidebarBorderColor\":\"FFFFFF\",\"profileUseBackgroundImage\":true,\"showAllInlineMedia\":false,\"friendsCount\":410,\"createdAt\":1396387577000,\"favouritesCount\":23,\"utcOffset\":-1,\"timeZone\":null,\"profileBackgroundImageUrlHttps\":\"https://abs.twimg.com/images/themes/theme18/bg.gif\",\"profileBackgroundTiled\":false,\"lang\":\"en\",\"statusesCount\":60,\"translator\":false,\"listedCount\":0,\"withheldInCountries\":null,\"protected\":false,\"contributorsEnabled\":false,\"profileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_normal.png\",\"biggerProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_bigger.png\",\"miniProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_mini.png\",\"originalProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R.png\",\"400x400ProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_400x400.png\",\"profileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_normal.png\",\"biggerProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_bigger.png\",\"miniProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_mini.png\",\"originalProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R.png\",\"400x400ProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_400x400.png\",\"defaultProfileImage\":false,\"defaultProfile\":false,\"profileBackgroundImageURL\":\"http://abs.twimg.com/images/themes/theme18/bg.gif\",\"profileBannerURL\":null,\"profileBannerRetinaURL\":null,\"profileBannerIPadURL\":null,\"profileBannerIPadRetinaURL\":null,\"profileBannerMobileURL\":null,\"profileBannerMobileRetinaURL\":null,\"profileBanner300x100URL\":null,\"profileBanner600x200URL\":null,\"profileBanner1500x500URL\":null,\"geoEnabled\":true,\"verified\":false,\"followRequestSent\":false,\"urlentity\":{\"start\":0,\"end\":0,\"url\":\"\",\"expandedURL\":\"\",\"displayURL\":\"\",\"text\":\"\"}},\"withheldInCountries\":null,\"quotedStatus\":null,\"quotedStatusId\":-1,\"quotedStatusPermalink\":null,\"truncated\":false,\"favorited\":false,\"retweeted\":false,\"retweet\":false,\"retweetedByMe\":false,\"possiblySensitive\":false,\"contributors\":[],\"urlentities\":[]}");
            when(mockFactory.updateStatus(exampleText)).thenReturn(statusEntity);
        } catch (TwitterException e) {
            Assert.fail("Test failed due to Twitter Exception: " + e.getMessage());
        }

        Assert.assertEquals(HttpURLConnection.HTTP_OK, statusResource.postStatus(exampleText).getStatus());
        Assert.assertEquals(statusEntity, statusResource.postStatus(exampleText).getEntity());
    }

    @Test
    public void statusLengthZeroTest() {
        exampleText = "";
        exceptionEntity = "No tweet text entered.";

        Assert.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, statusResource.postStatus(exampleText).getStatus());
        Assert.assertEquals(exceptionEntity, statusResource.postStatus(exampleText).getEntity());
    }

    @Test
    public void statusLengthLongTest() {
        exampleText = StringUtils.repeat("a", 281);
        exceptionEntity = "Tweet text surpassed 280 characters.";

        Assert.assertEquals(HttpURLConnection.HTTP_FORBIDDEN, statusResource.postStatus(exampleText).getStatus());
        Assert.assertEquals(exceptionEntity, statusResource.postStatus(exampleText).getEntity());
    }

    @Test
    public void statusTwitterExceptionTest() throws TwitterException {
        exampleText = "Exception.";
        exceptionEntity = "Whoops! Something went wrong. Try again later.";

        when(mockFactory.updateStatus(exampleText)).thenThrow(
                new TwitterException("Testing TwitterException.", new Exception(), HttpURLConnection.HTTP_INTERNAL_ERROR)
        );

        Assert.assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, statusResource.postStatus(exampleText).getStatus());
        Assert.assertEquals(exceptionEntity, statusResource.postStatus(exampleText).getEntity());
    }
}
