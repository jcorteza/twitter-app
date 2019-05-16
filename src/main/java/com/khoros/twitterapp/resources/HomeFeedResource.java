package com.khoros.twitterapp.resources;

import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class HomeFeedResource {

    private Twitter factory;

    public HomeFeedResource(Configuration conf) {
        this.factory = new TwitterFactory(conf).getInstance();
    }

    // constructor for unit testing using mock Twitter object
    public HomeFeedResource(Twitter factory) {
        this.factory = factory;
    }

    @GET
    public Response get() {
        try {
            List<Status> tweetsFeed = factory.getHomeTimeline();
            return Response.status(200).entity(tweetsFeed).build();
        } catch (TwitterException feedException) {
            return Response.status(feedException.getStatusCode()).entity("Whoops! Something went wrong. Try again later.").build();
        }
    }
}
