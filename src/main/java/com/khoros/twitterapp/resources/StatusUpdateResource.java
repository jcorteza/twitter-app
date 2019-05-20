package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.TwitterApp;
import twitter4j.TwitterFactory;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;

@Path("/tweet")
@Consumes("application/x-www-form-urlencoded")
@Produces("application/json")
public class StatusUpdateResource {

    public static final String NO_TWEET_TEXT_MSG = "No tweet text entered.";
    public static final String TWEET_TOO_LONG_MSG = "Tweet text surpassed " + TwitterApp.MAX_TWEET_LENGTH + " characters.";

    final Logger logger = LoggerFactory.getLogger(StatusUpdateResource.class);

    private Configuration conf;
    private Twitter factory;

    public StatusUpdateResource(Configuration conf) {
        this.conf = conf;
        this.factory = new TwitterFactory(conf).getInstance();
    }

    public StatusUpdateResource(Twitter mockFactory) {
        this.factory = mockFactory;
    }

    @POST
    public Response postStatus(@FormParam("message") String tweetText) {

        String statusText = tweetText.trim();

        logger.info ("Attempting to post a Twitter status update.");
        logger.debug("Twitter Configuration: {} — Status Update Text: {}", conf,statusText);

        try {
            if (statusText.length() == 0) {

                logger.info("Status length too short. length: {}", statusText.length());
                return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(StatusUpdateResource.NO_TWEET_TEXT_MSG).build();

            } else if (statusText.length() > TwitterApp.MAX_TWEET_LENGTH) {

                logger.info("Status length too long. length: {}", statusText.length());
                return Response.status(HttpURLConnection.HTTP_FORBIDDEN).entity(StatusUpdateResource.TWEET_TOO_LONG_MSG).build();

            } else {

                Status newStatus = factory.updateStatus(statusText);
                return Response.status(HttpURLConnection.HTTP_OK).entity(newStatus).build();

            }
        } catch (TwitterException tweetException) {

            logger.info("Status update aborted. Twitter Exception thrown." );

            if (tweetException.isCausedByNetworkIssue()) {

                logger.error("Twitter Exception Caused By Network Issues: {}", tweetException.isCausedByNetworkIssue());

            } else if (tweetException.exceededRateLimitation()) {

                logger.error("Request Exceeded Rate Limitation: {} — Current Rate Limit: {}", tweetException.exceededRateLimitation(),tweetException.getRateLimitStatus());

            } else if (tweetException.isErrorMessageAvailable()) {

                logger.error("Twitter Exception thrown. Error Message: {} — Exception Code: {}",
                        tweetException.getErrorMessage(),
                        tweetException.getExceptionCode()
                );

            } else {

                logger.error("Unknown Twitter Exception thrown. — Exception code: {}", tweetException.getExceptionCode());

            }

            return Response.status(tweetException.getStatusCode()).entity(TwitterApp.GENERAL_ERR_MSG).build();
        }
    }
}
