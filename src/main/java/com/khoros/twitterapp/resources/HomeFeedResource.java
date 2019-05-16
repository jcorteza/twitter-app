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

    final Logger logger = LoggerFactory.getLogger(HomeFeedResource.class);

    private Configuration conf;
    private Twitter factory = new TwitterFactory(conf).getInstance();

    public HomeFeedResource(Configuration conf) {
        this.conf = conf;
    }

    // constructor for unit testing using mock Twitter object
    public HomeFeedResource(Twitter factory) {
        this.factory = factory;
    }

    @GET
    public Response get() {

        logger.info("Attempting to retrieve Twitter Home timeline.");
        logger.debug("Configuration setup: {}", conf);

        try {

            List<Status> tweetsFeed = factory.getHomeTimeline();
            return Response.status(200).entity(tweetsFeed).build();

        } catch (TwitterException feedException) {

            logger.info("Timeline retrieval aborted. Twitter Exception thrown." );

            if (feedException.isCausedByNetworkIssue()) {

                logger.debug("Exception Caused By Network Issues: {}", feedException.isCausedByNetworkIssue());

            } else if (feedException.exceededRateLimitation()) {

                logger.debug("Current Rate Limit: {}", feedException.getRateLimitStatus());
                logger.debug("Exceed Rate Limitation: {}", feedException.exceededRateLimitation());

            } else {

                logger.debug("Twitter API Access Level: {}", feedException.getAccessLevel());
                logger.debug("TwitterException Error Message: {}", feedException.getErrorMessage());

            }

            return Response.status(feedException.getStatusCode()).entity("Whoops! Something went wrong. Try again later.").build();
        }
    }
}
