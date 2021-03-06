package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.services.TwitterServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class MainResource {

    private final Logger logger = LoggerFactory.getLogger(MainResource.class);
    private final TwitterService twitterService;

    @Inject
    public MainResource(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    @Path("/tweet")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    public Response postStatusUpdate(@FormParam("message") String tweetText) {

        logger.info("Accessing Twitter Service updateStatus feature.");

        String statusText = tweetText.trim();

        try {

            return twitterService.updateStatus(statusText)
                    .map(newStatus -> Response
                            .status(Response.Status.CREATED)
                            .entity(newStatus)
                            .build())
                    .orElse(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(TwitterService.GENERAL_ERR_MSG)
                            .build());

        } catch (TwitterServiceException twServiceException) {

            logger.info("MainResource process, postStatusUpdate, aborted. TwitterServiceExcpetion thrown.");

            return createExceptionResponseObject(twServiceException);
        }

    }

    @Path("/timeline")
    @GET
    public Response getHomeTimeline() {

        logger.info("Accessing Twitter Service getHomeTimeline feature.");

        try {

            return twitterService.getHomeTimeline()
                    .map(feedResponse -> Response
                            .status(Response.Status.OK)
                            .entity(feedResponse)
                            .build())
                    .orElse(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(TwitterService.GENERAL_ERR_MSG)
                            .build());

        } catch (TwitterServiceException twServiceException) {

            logger.info("MainResource process, getHomeTimeline, aborted. TwitterServiceException thrown." );

            return createExceptionResponseObject(twServiceException);

        }

    }

    @Path("/timeline/filter")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @GET
    public Response getFilteredTimeline(@QueryParam("keyword") String keyword) {

        logger.info("Accessing Twitter Service getHomeTimeline feature.");

        try {

            return twitterService.getFilteredHomeTimeline(keyword)
                    .map(feedResponse -> Response
                            .status(Response.Status.OK)
                            .entity(feedResponse)
                            .build())
                    .orElse(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(TwitterService.GENERAL_ERR_MSG)
                            .build());

        } catch (TwitterServiceException twServiceException) {

            logger.info("MainResource process, getFilteredTimeline, aborted. TwitterServiceException thrown." );

            return createExceptionResponseObject(twServiceException);

        }

    }

    @Path("/user-timeline")
    @GET
    public Response getUserTimeline() {

        logger.info("Accessing Twitter Service getUserTimeline feature.");

        try {

            return twitterService.getUserTimeline()
                    .map(feedResponse -> Response
                            .status(Response.Status.OK)
                            .entity(feedResponse)
                            .build())
                    .orElse(Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(TwitterService.GENERAL_ERR_MSG)
                            .build());

        } catch (TwitterServiceException twServiceException) {

            logger.info("MainResource process, getUserTimeline, aborted. TwitterServiceException thrown.");

            return createExceptionResponseObject(twServiceException);
        }

    }

    @Path("/reply")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    public Response replyToTweet(@FormParam("message") String statusText, @FormParam("inReplyTo") Long inReplyToID) {

        logger.info("Accessing Twitter Service replyToTweet feature.");

        try {

            return twitterService.replyToTweet(statusText.trim(), inReplyToID)
                    .map(newStatus -> Response
                        .status(Response.Status.CREATED)
                        .entity(newStatus)
                        .build())
                    .orElse(Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(TwitterService.GENERAL_ERR_MSG)
                        .build());

        } catch (TwitterServiceException twServiceException) {

            logger.info("MainResource process, replyToTweet, aborted. TwitterServiceExcpetion thrown.");

            return createExceptionResponseObject(twServiceException);
        }
    }

    private Response createExceptionResponseObject(TwitterServiceException exception) {

        if (exception.getCause() == null) {

            logger.error("TwitterServiceException — Error Message: {}",
                    exception.getMessage(),
                    exception);

            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(exception.getMessage())
                    .build();

        } else {

            logger.error("TwitterServiceException — Error Cause: {}: {}",
                    exception.getCause().getClass().getSimpleName(),
                    exception.getCause().getMessage(),
                    exception);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(exception.getMessage())
                    .build();

        }

    }
}
