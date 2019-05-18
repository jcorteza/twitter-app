package com.khoros.twitterapp;

import com.khoros.twitterapp.services.TwitterService;

import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import twitter4j.*;
import twitter4j.conf.Configuration;

public class TwitterServiceTest {

    private TwitterService twSingleton;
    private Twitter mockFactory;
    private TwitterResponse twResponse;
    private String testStatus;

    @Before
    public void setup() {

        twSingleton = TwitterService.getInstance();
        mockFactory = mock(Twitter.class);
        twSingleton.setMockTWFactory(mockFactory);

    }

    @After
    public void reset() {

        twSingleton.resetTWFactory();

    }

    @Test
    public void getInstanceTest() {

        Assert.assertSame(twSingleton, TwitterService.getInstance());

    }

    @Test
    public void updateStatusTestSuccess() {

        testStatus = "Tweet";
        twResponse = twSingleton.updateStatus(testStatus);

        Assert.assertThat(twResponse, is(instanceof Status));

    }

    @Test
    public void updateStatusTestException() throws TwitterException {

        testStatus = "Error";

        when(mockFactory.updateStatus(testStatus)).thenThrow(new TwitterException("Test"));
        twResponse = twSingleton.updateStatus(testStatus);

        Assert.assertThat(twResponse, is(instanceof TwitterException));

    }

    @Test
    public void getHomeTimelineTestSuccess() {

        twResponse = twSingleton.getHomeTimeline();

        Assert.assertThat(twResponse,);

    }

    @Test
    public void getHomeTimelineTestException() throws TwitterException {


        when(mockFactory.getHomeTimeline()).thenThrow(new TwitterException("Test"));
        twResponse = twSingleton.getHomeTimeline();

        Assert.assertThat(twResponse,);

    }

    @Test
    public void setTWFactoryTestMock() {

        Assert.assertEquals(twSingleton, TwitterService.getInstance());

    }

    @Test
    public void setTWFactoryTestConfig() {

        Configuration mockConfig = mock(Configuration.class);
        Twitter testFactory = new TwitterFactory(mockConfig).getInstance();
        twSingleton.setTWFactory(mockConfig);

        Assert.assertThat(testFactory, twSingleton.getFatory());

    }

}
