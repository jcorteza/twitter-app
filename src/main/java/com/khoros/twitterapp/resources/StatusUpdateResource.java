package com.khoros.twitterapp.resources;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;

@Path("/tweet")
@Consumes("application/x-www-form-urlencoded")
@Produces("application/json")
public class StatusUpdateResource {

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + StatusUpdateResource.MAX_TWEET_LENGTH + " characters.";
    private Twitter factory;

    public StatusUpdateResource(Configuration conf) {
        this.factory = new TwitterFactory(conf).getInstance();
    }

    public StatusUpdateResource(Twitter mockFactory) {
        this.factory = mockFactory;
    }

    @POST
    public Response postStatus(@FormParam("message") String tweetText) {
        String statusText = tweetText.trim();
        try {
            if (statusText.length() == 0) {
                return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(StatusUpdateResource.NO_TWEET_TEXT_MSG).build();
            } else if (statusText.length() > StatusUpdateResource.MAX_TWEET_LENGTH) {
                return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(StatusUpdateResource.TWEET_TOO_LONG_MSG).build();
            } else {
                Status newStatus = factory.updateStatus(statusText);
                return Response.status(HttpURLConnection.HTTP_OK).entity(newStatus).build();
            }
        } catch (TwitterException tweetException) {
            return Response.status(tweetException.getStatusCode()).entity("Whoops! Something went wrong. Try again later.").build();
        }
    }
}
