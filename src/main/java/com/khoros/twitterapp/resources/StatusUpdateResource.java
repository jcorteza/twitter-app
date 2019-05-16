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

    private int maxTweetLength;
    private Twitter factory;

    public StatusUpdateResource(Configuration conf, int maxTweetLength) {
        this.maxTweetLength = maxTweetLength;
        this.factory = new TwitterFactory(conf).getInstance();
    }

    public StatusUpdateResource(Twitter mockFactory) {
        this.factory = mockFactory;
        this.maxTweetLength = 280;
    }

    @POST
    public Response postStatus(@FormParam("message") String tweetText) {
        String statusText = tweetText.trim();
        try {
            if (statusText.length() == 0) {
                return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity("No tweet text entered.").build();
            } else if (statusText.length() > maxTweetLength) {
                return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity("Tweet text surpassed 280 characters.").build();
            } else {
                Status newStatus = factory.updateStatus(statusText);
                return Response.status(HttpURLConnection.HTTP_OK).entity(newStatus).build();
            }
        } catch (TwitterException tweetException) {
            return Response.status(tweetException.getStatusCode()).entity("Whoops! Something went wrong. Try again later.").build();
        }
    }
}
