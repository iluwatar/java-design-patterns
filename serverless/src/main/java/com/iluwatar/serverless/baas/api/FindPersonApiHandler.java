/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.iluwatar.serverless.baas.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * find person from persons collection Created by dheeraj.mummar on 3/5/18.
 */
public class FindPersonApiHandler extends AbstractDynamoDbHandler<Person>
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private static final Logger LOG = LoggerFactory.getLogger(FindPersonApiHandler.class);
  private static final Integer SUCCESS_STATUS_CODE = 200;

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent req, Context ctx) {
    req.getPathParameters().forEach(FindPersonApiHandler::logKeyValue);
    var id = req.getPathParameters().get("id");
    var person = this.getDynamoDbMapper().load(Person.class, id);
    return apiGatewayProxyResponseEvent(SUCCESS_STATUS_CODE, person);
  }

  private static void logKeyValue(String key, String value) {
    LOG.info(key + "=" + value);
  }

}
