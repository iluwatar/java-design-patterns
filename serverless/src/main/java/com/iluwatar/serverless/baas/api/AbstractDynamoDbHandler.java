package com.iluwatar.serverless.baas.api;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * abstract dynamodb handler
 * @param <T> - serializable collection
 */
public abstract class AbstractDynamoDbHandler<T extends Serializable> {
  private DynamoDBMapper dynamoDbMapper;

  private ObjectMapper objectMapper;

  public AbstractDynamoDbHandler() {
    this.initAmazonDynamoDb();
    this.objectMapper = new ObjectMapper();
  }

  private void initAmazonDynamoDb() {
    AmazonDynamoDB amazonDynamoDb = AmazonDynamoDBClientBuilder
        .standard()
        .withRegion(Regions.US_EAST_1)
        .build();

    this.dynamoDbMapper = new DynamoDBMapper(amazonDynamoDb);
  }

  protected DynamoDBMapper getDynamoDbMapper() {
    return this.dynamoDbMapper;
  }

  protected ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public void setDynamoDbMapper(DynamoDBMapper dynamoDbMapper) {
    this.dynamoDbMapper = dynamoDbMapper;
  }

  protected Map<String, String> headers() {
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "application/json");

    return headers;
  }

  /**
   * API Gateway response
   *
   * @param statusCode - status code
   * @param body - Object body
   * @return - api gateway proxy response
   */
  protected APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent(Integer statusCode, T body) {
    APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent =
        new APIGatewayProxyResponseEvent().withHeaders(headers());
    try {
      apiGatewayProxyResponseEvent
          .withStatusCode(statusCode)
          .withBody(getObjectMapper()
              .writeValueAsString(body));

    } catch (JsonProcessingException jsonProcessingException) {
      throw new RuntimeException(jsonProcessingException);
    }

    return apiGatewayProxyResponseEvent;
  }
}
