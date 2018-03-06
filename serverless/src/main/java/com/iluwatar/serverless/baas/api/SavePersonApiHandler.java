package com.iluwatar.serverless.baas.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.iluwatar.serverless.baas.model.Person;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * save person into persons collection
 * Created by dheeraj.mummar on 3/4/18.
 */
public class SavePersonApiHandler extends AbstractDynamoDbHandler
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private static final Logger LOG = Logger.getLogger(SavePersonApiHandler.class);
  private static final Integer CREATED_STATUS_CODE = 201;
  private static final Integer BAD_REQUEST_STATUS_CODE = 400;

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent
                                                apiGatewayProxyRequestEvent, Context context) {
    APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent;
    Person person;
    try {
      person = getObjectMapper().readValue(apiGatewayProxyRequestEvent.getBody(), Person.class);
      getDynamoDbMapper().save(person);
      apiGatewayProxyResponseEvent = apiGatewayProxyResponseEvent(CREATED_STATUS_CODE, person);
    } catch (IOException ioException) {
      LOG.error("unable to parse body", ioException);
      apiGatewayProxyResponseEvent = apiGatewayProxyResponseEvent(BAD_REQUEST_STATUS_CODE, null);
    }

    return apiGatewayProxyResponseEvent;
  }
}
