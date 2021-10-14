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

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.iluwatar.serverless.baas.model.Person;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Unit tests for FindPersonApiHandler Created by dheeraj.mummar on 3/5/18.
 */
class FindPersonApiHandlerTest {

  private FindPersonApiHandler findPersonApiHandler;

  @Mock
  private DynamoDBMapper dynamoDbMapper;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    this.findPersonApiHandler = new FindPersonApiHandler();
    this.findPersonApiHandler.setDynamoDbMapper(dynamoDbMapper);
  }

  @Test
  void handleRequest() {
    findPersonApiHandler.handleRequest(apiGatewayProxyRequestEvent(), mock(Context.class));
    verify(dynamoDbMapper, times(1)).load(Person.class, "37e7a1fe-3544-473d-b764-18128f02d72d");
  }

  private APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent() {
    var request = new APIGatewayProxyRequestEvent();
    return request.withPathParamters(Map.of("id", "37e7a1fe-3544-473d-b764-18128f02d72d"));
  }
}
