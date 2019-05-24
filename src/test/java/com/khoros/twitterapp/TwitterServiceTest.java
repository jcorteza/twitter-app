package com.khoros.twitterapp;

import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.models.Status;

import static org.mockito.Mockito.*;

import com.khoros.twitterapp.services.TwitterServiceException;
import org.junit.Test;
import org.junit.Before;
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
    private ResponseList<twitter4j.Status> twResponse;
    private twitter4j.Status exampleStatus;

    @Before
    public void setup() {

        twSingleton = TwitterService.getInstance();
        mockFactory = mock(Twitter.class);
        twSingleton.setTWFactory(mockFactory);
        testStatusText = "Tweet Test";
        twResponse = new ResponseImplTest<twitter4j.Status>();
        exampleStatus = new Twitter4jStatusImpl();
        twResponse.add(exampleStatus);

    }

    @Test
    public void updateStatusTestSuccess() {

        Status serviceResponse = null;

        try {

            when(mockFactory.updateStatus(testStatusText)).thenReturn(exampleStatus);
            serviceResponse = twSingleton.updateStatus(testStatusText);

        } catch (TwitterException e) {

            Assert.fail("Test failed due to Twitter Exception");

        } catch (TwitterServiceException e) {

            Assert.fail("Test failed due to Twitter Service Exception.");

        }

        Assert.assertEquals(exampleStatus.getText(), serviceResponse.getMessage());
        Assert.assertEquals(exampleStatus.getCreatedAt(), serviceResponse.getCreatedAt());
        Assert.assertEquals(exampleStatus.getUser().getProfileImageURL(), serviceResponse.getUser().getProfileImageUrl());
        Assert.assertEquals(exampleStatus.getUser().getScreenName(), serviceResponse.getUser().getTwHandle());
        Assert.assertEquals(exampleStatus.getUser().getName(), serviceResponse.getUser().getName());

    }

    @Test
    public void getHomeTimelineTestSuccess() {

        List<Status> responseList = null;

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
    public void getHomeTimelineFilteredSuccess() {

        testStatusText = "Tweet";

        try {

            when(mockFactory.getHomeTimeline()).thenReturn(twResponse);

            try {

                Assert.assertEquals(exampleStatus.getText(), twSingleton.getHomeTimelineFilteredByKeyword(testStatusText).get(0).getMessage());

            } catch (TwitterServiceException twitterServiceException) {

                Assert.fail("Unit test failed to TwitterServiceException");

            }

        } catch (TwitterException twittterException) {

            Assert.fail("Unit test failed due to TwitterException.");

        }

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
        twSingleton.setTWFactory(testConfig);

        Assert.assertEquals(new TwitterFactory(testConfig).getInstance(), TwitterService.getInstance().getTwitterFactory());
        Assert.assertEquals(testConfig, TwitterService.getInstance().getTwitterFactory().getConfiguration());

    }

}
