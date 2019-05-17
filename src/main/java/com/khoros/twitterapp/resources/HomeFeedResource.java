package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.TwitterApp;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.net.HttpURLConnection;
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
    private Twitter factory;

    public HomeFeedResource(Configuration conf) {
        this.conf = conf;
        this.factory = new TwitterFactory(conf).getInstance();
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
            return Response.status(HttpURLConnection.HTTP_OK).entity(tweetsFeed).build();

        } catch (TwitterException feedException) {

            logger.info("Timeline retrieval aborted. Twitter Exception thrown." );

            if (feedException.isCausedByNetworkIssue()) {

                logger.error("Twitter Exception Caused By Network Issues: {}", feedException.isCausedByNetworkIssue());

            } else if (feedException.exceededRateLimitation()) {

                logger.error("Request Exceeded Rate Limitation: {}\nCurrent Rate Limit: {}", feedException.getRateLimitStatus(),feedException.exceededRateLimitation());

            } else {

                logger.error("Twitter Exception thrown. Error Message: {}", feedException.getErrorMessage(), new Exception("Twitter Exception"));

            }

            return Response.status(feedException.getStatusCode()).entity(TwitterApp.GENERAL_ERR_MSG).build();

        }
    }
}
