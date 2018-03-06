package com.illuwatar.serverless.baas.api;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iluwatar.serverless.baas.api.SavePersonApiHandler;
import com.iluwatar.serverless.baas.model.Address;
import com.iluwatar.serverless.baas.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by dheeraj.mummar on 3/4/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class SavePersonApiHandlerTest {

  private SavePersonApiHandler savePersonApiHandler;

  @Mock
  private DynamoDBMapper dynamoDbMapper;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() {
    this.savePersonApiHandler = new SavePersonApiHandler();
    this.savePersonApiHandler.setDynamoDbMapper(dynamoDbMapper);
  }

  @Test
  public void handleRequestSavePersonSuccessful() throws JsonProcessingException {
    Person person = newPerson();
    APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent =
        this.savePersonApiHandler
            .handleRequest(apiGatewayProxyRequestEvent(objectMapper.writeValueAsString(person)), mock(Context.class));
    verify(dynamoDbMapper, times(1)).save(person);
    Assert.assertNotNull(apiGatewayProxyResponseEvent);
    Assert.assertEquals(new Integer(201), apiGatewayProxyResponseEvent.getStatusCode());
  }

  @Test
  public void handleRequestSavePersonException() {
    APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent =
        this.savePersonApiHandler
            .handleRequest(apiGatewayProxyRequestEvent("invalid sample request"), mock(Context.class));
    Assert.assertNotNull(apiGatewayProxyResponseEvent);
    Assert.assertEquals(new Integer(400), apiGatewayProxyResponseEvent.getStatusCode());
  }

  private APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent(String body) {
    APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent = new APIGatewayProxyRequestEvent();
    return apiGatewayProxyRequestEvent.withBody(body);
  }

  private Person newPerson() {
    Person person = new Person();
    person.setFirstName("Thor");
    person.setLastName("Odinson");
    Address address = new Address();
    address.setAddressLineOne("1 Odin ln");
    address.setCity("Asgard");
    address.setState("country of the Gods");
    address.setZipCode("00001");
    person.setAddress(address);

    return person;
  }
}
