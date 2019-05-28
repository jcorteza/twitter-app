package com.khoros.twitterapp.resources;

import com.khoros.twitterapp.models.Status;
import com.khoros.twitterapp.services.TwitterService;
import com.khoros.twitterapp.services.TwitterServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class MainResource {

    private final Logger logger = LoggerFactory.getLogger(MainResource.class);
    private TwitterService twitterService = TwitterService.getInstance();

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

            logger.info("Twitter Service process aborted. Twitter Service Excpetion thrown.");

            if (twServiceException.getCause() == null) {

                logger.error("Twitter Service Exception — Error Message: {}",
                        twServiceException.getMessage(),
                        twServiceException);

                return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(twServiceException.getMessage())
                        .build();

            } else {

                logger.error("Twitter Service Exception — Error Cause: {}",
                        twServiceException.getCause().getMessage(),
                        twServiceException);

                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(twServiceException.getCause().getMessage())
                        .build();

            }
        }

    }

    @Path("/timeline")
    @GET
    public Response getHomeTimeline() {

        logger.info("Accessing Twitter Service getHomeTimeline feature.");

        try {

            Optional<List<Status>> feedResponse = twitterService.getHomeTimeline();

            if (feedResponse.isPresent()) {

                return Response
                        .status(Response.Status.OK)
                        .entity(feedResponse)
                        .build();

            } else {

                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(TwitterService.GENERAL_ERR_MSG)
                        .build();

            }

        } catch (TwitterServiceException twServiceException) {

            logger.info("Twitter Service process aborted. Twitter Service Exception thrown." );

            if (twServiceException.getCause() == null) {

                logger.error("Twitter Service Exception — Error Message: {}",
                        twServiceException.getMessage(),
                        twServiceException);

                return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(twServiceException.getMessage())
                        .build();

            } else {

                logger.error("Twitter Service Exception — Error Cause: {}",
                        twServiceException.getCause().getMessage(),
                        twServiceException);

                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(twServiceException.getCause().getMessage())
                        .build();

            }

        }

    }

    @Path("/timeline/{filter}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @GET
    public Response getFilteredTimeline(@PathParam("filter") String keyword) {

        logger.info("Accessing Twitter Service getHomeTimeline feature.");

        try {

            Optional<List<Status>> feedResponse = twitterService.getHomeTimeline();

            if(feedResponse.isPresent()) {

                    return Response
                            .status(Response.Status.OK)
                            .entity(twitterService.getHomeTimelineFilteredByKeyword(keyword))
                            .build();

            } else {

                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(TwitterService.GENERAL_ERR_MSG)
                        .build();

            }

        } catch (TwitterServiceException twServiceException) {

            logger.info("Twitter Service process aborted. Twitter Service Exception thrown." );

            if (twServiceException.getCause() == null) {

                logger.error("Twitter Service Exception — Error Message: {}",
                        twServiceException.getMessage(),
                        twServiceException);

                return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(twServiceException.getMessage())
                        .build();

            } else {

                logger.error("Twitter Service Exception — Error Cause: {}",
                        twServiceException.getCause().getMessage(),
                        twServiceException);

                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(twServiceException.getCause().getMessage())
                        .build();

            }

        }

    }
}
