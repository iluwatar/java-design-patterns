/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.serverless.baas.api;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.Map;

/**
 * abstract dynamodb handler.
 *
 * @param <T> - serializable collection
 */
public abstract class AbstractDynamoDbHandler<T extends Serializable> {
  private DynamoDBMapper dynamoDbMapper;

  private final ObjectMapper objectMapper;

  public AbstractDynamoDbHandler() {
    this.initAmazonDynamoDb();
    this.objectMapper = new ObjectMapper();
  }

  private void initAmazonDynamoDb() {
    var amazonDynamoDb = AmazonDynamoDBClientBuilder
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
    return Map.of("Content-Type", "application/json");
  }

  /**
   * API Gateway response.
   *
   * @param statusCode - status code
   * @param body       - Object body
   * @return - api gateway proxy response
   */
  protected APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent(Integer statusCode, T body) {
    var apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent().withHeaders(headers());
    try {
      apiGatewayProxyResponseEvent
          .withStatusCode(statusCode)
          .withBody(getObjectMapper().writeValueAsString(body));
    } catch (JsonProcessingException jsonProcessingException) {
      throw new RuntimeException(jsonProcessingException);
    }

    return apiGatewayProxyResponseEvent;
  }
}
