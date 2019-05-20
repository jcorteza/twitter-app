package com.khoros.twitterapp;

import com.khoros.twitterapp.services.TwitterService;

import static org.mockito.Mockito.*;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.ResponseList;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterServiceTest {

    private TwitterService twSingleton;
    private Twitter mockFactory;
    private TwitterResponse twResponse;
    private String testStatusText;

    @Before
    public void setup() {

        twSingleton = TwitterService.getInstance();
        mockFactory = mock(Twitter.class);
        twSingleton.setMockTWFactory(mockFactory);
        testStatusText = "Tweet Test";

    }

    @After
    public void reset() {

        twSingleton.resetTWFactory();

    }

    @Test
    public void getInstanceTest() {

        Assert.assertEquals(twSingleton, TwitterService.getInstance());
        Assert.assertThat(twSingleton, IsInstanceOf.<TwitterService>instanceOf(TwitterService.class));

    }

    @Test
    public void updateStatusTestSuccess() {

        Status testStatus = null;

        try {

            testStatus = TwitterObjectFactory.createStatus("{\"rateLimitStatus\":null,\"accessLevel\":2,\"createdAt\":1557851237000,\"id\":1128335988466622465,\"text\":\"" + testStatusText + "\",\"displayTextRangeStart\":0,\"displayTextRangeEnd\":12,\"source\":\"<a href=\\\"https://github.com/jcorteza\\\" rel=\\\"nofollow\\\">Tweeting—Khoros Lab C104</a>\",\"inReplyToStatusId\":-1,\"inReplyToUserId\":-1,\"favoriteCount\":0,\"inReplyToScreenName\":null,\"geoLocation\":null,\"place\":null,\"retweetCount\":0,\"lang\":\"en\",\"retweetedStatus\":null,\"userMentionEntities\":[],\"hashtagEntities\":[],\"mediaEntities\":[],\"symbolEntities\":[],\"currentUserRetweetId\":-1,\"scopes\":null,\"user\":{\"rateLimitStatus\":null,\"accessLevel\":0,\"id\":2422687974,\"name\":\"Josephine Cortez\",\"email\":null,\"screenName\":\"joC346\",\"location\":\"\",\"description\":\"\",\"descriptionURLEntities\":[],\"url\":null,\"followersCount\":41,\"status\":null,\"profileBackgroundColor\":\"ACDED6\",\"profileTextColor\":\"333333\",\"profileLinkColor\":\"94D487\",\"profileSidebarFillColor\":\"DDEEF6\",\"profileSidebarBorderColor\":\"FFFFFF\",\"profileUseBackgroundImage\":true,\"showAllInlineMedia\":false,\"friendsCount\":410,\"createdAt\":1396387577000,\"favouritesCount\":23,\"utcOffset\":-1,\"timeZone\":null,\"profileBackgroundImageUrlHttps\":\"https://abs.twimg.com/images/themes/theme18/bg.gif\",\"profileBackgroundTiled\":false,\"lang\":\"en\",\"statusesCount\":60,\"translator\":false,\"listedCount\":0,\"withheldInCountries\":null,\"protected\":false,\"contributorsEnabled\":false,\"profileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_normal.png\",\"biggerProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_bigger.png\",\"miniProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_mini.png\",\"originalProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R.png\",\"400x400ProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_400x400.png\",\"profileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_normal.png\",\"biggerProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_bigger.png\",\"miniProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_mini.png\",\"originalProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R.png\",\"400x400ProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_400x400.png\",\"defaultProfileImage\":false,\"defaultProfile\":false,\"profileBackgroundImageURL\":\"http://abs.twimg.com/images/themes/theme18/bg.gif\",\"profileBannerURL\":null,\"profileBannerRetinaURL\":null,\"profileBannerIPadURL\":null,\"profileBannerIPadRetinaURL\":null,\"profileBannerMobileURL\":null,\"profileBannerMobileRetinaURL\":null,\"profileBanner300x100URL\":null,\"profileBanner600x200URL\":null,\"profileBanner1500x500URL\":null,\"geoEnabled\":true,\"verified\":false,\"followRequestSent\":false,\"urlentity\":{\"start\":0,\"end\":0,\"url\":\"\",\"expandedURL\":\"\",\"displayURL\":\"\",\"text\":\"\"}},\"withheldInCountries\":null,\"quotedStatus\":null,\"quotedStatusId\":-1,\"quotedStatusPermalink\":null,\"truncated\":false,\"favorited\":false,\"retweeted\":false,\"retweet\":false,\"retweetedByMe\":false,\"possiblySensitive\":false,\"contributors\":[],\"urlentities\":[]}");
            when(twSingleton.updateStatus(testStatusText)).thenReturn(testStatus);
            // twResponse = twSingleton.updateStatus(testStatusText);

        } catch (TwitterException e) {

            Assert.fail("Test failed due to Twitter Exception: " + e.getMessage());

        }

        Assert.assertThat(twSingleton.updateStatus(testStatusText), IsInstanceOf.<TwitterResponse>instanceOf(Status.class));
        // Assert.assertEquals(twResponse, twSingleton.updateStatus(testStatusText));
        Assert.assertEquals(testStatus, twSingleton.updateStatus(testStatusText));

    }

    @Test
    public void updateStatusTestException() throws TwitterException {

        when(mockFactory.updateStatus(testStatusText)).thenThrow(new TwitterException("Test"));
        twResponse = twSingleton.updateStatus(testStatusText);

        Assert.assertThat(twResponse, IsInstanceOf.<TwitterResponse>instanceOf(TwitterException.class));

    }

    @Test
    public void getHomeTimelineTestSuccess() {

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(new ResponseImplTest<Status>());
            twResponse = twSingleton.getHomeTimeline();

        } catch (TwitterException e) {

            Assert.fail("Test failed due to Twitter Exception: " + e.getMessage());

        }

        Assert.assertThat(twResponse, IsInstanceOf.<TwitterResponse>instanceOf(ResponseList.class));
        Assert.assertEquals(twResponse, twSingleton.getHomeTimeline());

    }

    @Test
    public void getHomeTimelineTestException() throws TwitterException {


        when(mockFactory.getHomeTimeline()).thenThrow(new TwitterException("Test"));
        twResponse = twSingleton.getHomeTimeline();

        Assert.assertThat(twResponse, IsInstanceOf.<TwitterResponse>instanceOf(TwitterException.class));

    }

    @Test
    public void setTWFactoryTestMock() {

        Assert.assertEquals(twSingleton, TwitterService.getInstance());

    }

    @Test
    public void getFactoryTest() {

        Assert.assertThat(twSingleton.getFactory(), IsInstanceOf.<Twitter>instanceOf(Twitter.class));
        Assert.assertEquals(twSingleton.getFactory(), TwitterService.getInstance().getFactory());
    }

    @Test
    public void setTWFactoryTestConfig() {

        ConfigurationBuilder testCB = new ConfigurationBuilder();
        Configuration testConfig = testCB.setDebugEnabled(true)
                .setOAuthAccessToken("authorizationToken")
                .setOAuthAccessTokenSecret("authorizationTokenSecret")
                .setOAuthConsumerKey("authorizationConsumerKey")
                .setOAuthConsumerSecret("authorizationConsumerSecret")
                .build();
        twSingleton.setTWFactory(testConfig);

        Assert.assertEquals(twSingleton.getFactory(), TwitterService.getInstance().getFactory());

    }

}
