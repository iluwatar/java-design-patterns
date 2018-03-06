package com.illuwatar.serverless.baas.api;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.iluwatar.serverless.baas.api.FindPersonApiHandler;
import com.iluwatar.serverless.baas.api.SavePersonApiHandler;
import com.iluwatar.serverless.baas.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by dheeraj.mummar on 3/5/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class FindPersonApiHandlerTest {

  private FindPersonApiHandler findPersonApiHandler;

  @Mock
  private DynamoDBMapper dynamoDbMapper;

  @Before
  public void setUp() {
    this.findPersonApiHandler = new FindPersonApiHandler();
    this.findPersonApiHandler.setDynamoDbMapper(dynamoDbMapper);
  }

  @Test
  public void handleRequest() {
    findPersonApiHandler.handleRequest(apiGatewayProxyRequestEvent(), mock(Context.class));
    verify(dynamoDbMapper, times(1)).load(Person.class, "37e7a1fe-3544-473d-b764-18128f02d72d");
  }

  private APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent() {
    return new APIGatewayProxyRequestEvent()
        .withPathParamters(Collections
            .singletonMap("id", "37e7a1fe-3544-473d-b764-18128f02d72d"));
  }
}
