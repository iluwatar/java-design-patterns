package com.iluwatar.serverless.baas.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.iluwatar.serverless.baas.model.Person;
import org.apache.log4j.Logger;

/**
 * find person from persons collection
 * Created by dheeraj.mummar on 3/5/18.
 */
public class FindPersonApiHandler extends AbstractDynamoDbHandler
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private static final Logger LOG = Logger.getLogger(FindPersonApiHandler.class);
  private static final Integer SUCCESS_STATUS_CODE = 200;

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent,
                                                    Context context) {
    LOG.info(apiGatewayProxyRequestEvent.getPathParameters());
    Person person = this.getDynamoDbMapper().load(Person.class, apiGatewayProxyRequestEvent
        .getPathParameters().get("id"));

    return apiGatewayProxyResponseEvent(SUCCESS_STATUS_CODE, person);
  }
}
