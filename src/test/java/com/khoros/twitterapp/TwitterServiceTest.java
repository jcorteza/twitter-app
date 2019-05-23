package com.khoros.twitterapp;

import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.models.User;
import com.khoros.twitterapp.models.Status;

import static org.mockito.Mockito.*;

import com.khoros.twitterapp.services.TwitterServiceException;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

public class TwitterServiceTest {

    // private Configuration originalConfig;
    private TwitterService twSingleton;
    private Twitter mockFactory;
    private String testStatusText;

    @Before
    public void setup() {

        twSingleton = TwitterService.getInstance();
        // originalConfig = twSingleton.getTwitterFactory().getConfiguration();
        mockFactory = mock(Twitter.class);
        twSingleton.setTWFactory(mockFactory);
        testStatusText = "Tweet Test";

    }

    @After
    public void reset() {

        // twSingleton.setTWFactory(originalConfig, false);

    }

    @Test
    public void updateStatusTestSuccess() {

        twitter4j.Status testStatus = null;
        Status serviceResponse = null;

        try {

            testStatus = TwitterObjectFactory.createStatus("{\"rateLimitStatus\":null,\"accessLevel\":2,\"createdAt\":1557851237000,\"id\":1128335988466622465,\"text\":\"" + testStatusText + "\",\"displayTextRangeStart\":0,\"displayTextRangeEnd\":12,\"source\":\"<a href=\\\"https://github.com/jcorteza\\\" rel=\\\"nofollow\\\">Tweeting—Khoros Lab C104</a>\",\"inReplyToStatusId\":-1,\"inReplyToUserId\":-1,\"favoriteCount\":0,\"inReplyToScreenName\":null,\"geoLocation\":null,\"place\":null,\"retweetCount\":0,\"lang\":\"en\",\"retweetedStatus\":null,\"userMentionEntities\":[],\"hashtagEntities\":[],\"mediaEntities\":[],\"symbolEntities\":[],\"currentUserRetweetId\":-1,\"scopes\":null,\"user\":{\"rateLimitStatus\":null,\"accessLevel\":0,\"id\":2422687974,\"name\":\"Josephine Cortez\",\"email\":null,\"screenName\":\"joC346\",\"location\":\"\",\"description\":\"\",\"descriptionURLEntities\":[],\"url\":null,\"followersCount\":41,\"status\":null,\"profileBackgroundColor\":\"ACDED6\",\"profileTextColor\":\"333333\",\"profileLinkColor\":\"94D487\",\"profileSidebarFillColor\":\"DDEEF6\",\"profileSidebarBorderColor\":\"FFFFFF\",\"profileUseBackgroundImage\":true,\"showAllInlineMedia\":false,\"friendsCount\":410,\"createdAt\":1396387577000,\"favouritesCount\":23,\"utcOffset\":-1,\"timeZone\":null,\"profileBackgroundImageUrlHttps\":\"https://abs.twimg.com/images/themes/theme18/bg.gif\",\"profileBackgroundTiled\":false,\"lang\":\"en\",\"statusesCount\":60,\"translator\":false,\"listedCount\":0,\"withheldInCountries\":null,\"protected\":false,\"contributorsEnabled\":false,\"profileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_normal.png\",\"biggerProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_bigger.png\",\"miniProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_mini.png\",\"originalProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R.png\",\"400x400ProfileImageURL\":\"http://pbs.twimg.com/profile_images/513270910254473218/Or70322R_400x400.png\",\"profileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_normal.png\",\"biggerProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_bigger.png\",\"miniProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_mini.png\",\"originalProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R.png\",\"400x400ProfileImageURLHttps\":\"https://pbs.twimg.com/profile_images/513270910254473218/Or70322R_400x400.png\",\"defaultProfileImage\":false,\"defaultProfile\":false,\"profileBackgroundImageURL\":\"http://abs.twimg.com/images/themes/theme18/bg.gif\",\"profileBannerURL\":null,\"profileBannerRetinaURL\":null,\"profileBannerIPadURL\":null,\"profileBannerIPadRetinaURL\":null,\"profileBannerMobileURL\":null,\"profileBannerMobileRetinaURL\":null,\"profileBanner300x100URL\":null,\"profileBanner600x200URL\":null,\"profileBanner1500x500URL\":null,\"geoEnabled\":true,\"verified\":false,\"followRequestSent\":false,\"urlentity\":{\"start\":0,\"end\":0,\"url\":\"\",\"expandedURL\":\"\",\"displayURL\":\"\",\"text\":\"\"}},\"withheldInCountries\":null,\"quotedStatus\":null,\"quotedStatusId\":-1,\"quotedStatusPermalink\":null,\"truncated\":false,\"favorited\":false,\"retweeted\":false,\"retweet\":false,\"retweetedByMe\":false,\"possiblySensitive\":false,\"contributors\":[],\"urlentities\":[]}");
            when(mockFactory.updateStatus(testStatusText)).thenReturn(testStatus);
            serviceResponse = twSingleton.updateStatus(testStatusText);

        } catch (TwitterException e) {

            Assert.fail("Test failed due to Twitter Exception");

        } catch (TwitterServiceException e) {

            Assert.fail("Test failed due to Twitter Service Exception.");

        }

        Assert.assertEquals(testStatus.getText(), serviceResponse.getMessage());
        Assert.assertEquals(testStatus.getCreatedAt(), serviceResponse.getCreatedAt());
        Assert.assertEquals(testStatus.getUser().getProfileImageURL(), serviceResponse.getUser().getProfileImageUrl());
        Assert.assertEquals(testStatus.getUser().getScreenName(), serviceResponse.getUser().getTwHandle());
        Assert.assertEquals(testStatus.getUser().getName(), serviceResponse.getUser().getName());

    }

    @Test
    public void getHomeTimelineTestSuccess() {

        ResponseList<twitter4j.Status> twResponse = new ResponseImplTest<twitter4j.Status>();
        twitter4j.Status exampleStatus = new Twitter4jStatusImpl();
        twResponse.add(exampleStatus);

        List<Status> responseList = null;
        User responseUser;

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(twResponse);
            responseList = twSingleton.getHomeTimeline();

        } catch (Exception e) {

            Assert.fail("Test failed due to Twitter Exception");

        }

        Assert.assertEquals(exampleStatus.getText(), responseList.get(0).getMessage());
        Assert.assertEquals(exampleStatus.getCreatedAt(), responseList.get(0).getCreatedAt());
        Assert.assertEquals(exampleStatus.getUser().getName(), responseList.get(0).getUser().getName());
        Assert.assertEquals(exampleStatus.getUser().getScreenName(), responseList.get(0).getUser().getTwHandle());
        Assert.assertEquals(exampleStatus.getUser().getProfileImageURL(), responseList.get(0).getUser().getProfileImageUrl());

    }

    @Test
    public void getFactoryTest() {

        Assert.assertEquals(mockFactory, TwitterService.getInstance().getTwitterFactory());

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
        twSingleton.setTWFactory(testConfig, true);

        Assert.assertEquals(new TwitterFactory(testConfig).getInstance(), TwitterService.getInstance().getTwitterFactory());
        Assert.assertEquals(testConfig, TwitterService.getInstance().getTwitterFactory().getConfiguration());

    }

}
