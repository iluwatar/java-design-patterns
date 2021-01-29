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

package com.iluwatar.serverless.faas.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.iluwatar.serverless.faas.ApiGatewayResponse;
import com.iluwatar.serverless.faas.LambdaInfo;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LambdaInfoApiHandler - simple api to get lambda context Created by dheeraj.mummar on 2/5/18.
 */
public class LambdaInfoApiHandler
    implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

  private static final Logger LOG = LoggerFactory.getLogger(LambdaInfoApiHandler.class);
  private static final Integer SUCCESS_STATUS_CODE = 200;


  @Override
  public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    LOG.info("received: " + input);

    return new ApiGatewayResponse.ApiGatewayResponseBuilder<LambdaInfo>()
        .headers(headers())
        .statusCode(SUCCESS_STATUS_CODE)
        .body(lambdaInfo(context))
        .build();
  }

  /**
   * lambdaInfo.
   *
   * @param context - Lambda context
   * @return LambdaInfo
   */
  private LambdaInfo lambdaInfo(Context context) {
    var lambdaInfo = new LambdaInfo();
    lambdaInfo.setAwsRequestId(context.getAwsRequestId());
    lambdaInfo.setFunctionName(context.getFunctionName());
    lambdaInfo.setFunctionVersion(context.getFunctionVersion());
    lambdaInfo.setLogGroupName(context.getLogGroupName());
    lambdaInfo.setLogStreamName(context.getLogStreamName());
    lambdaInfo.setMemoryLimitInMb(context.getMemoryLimitInMB());
    return lambdaInfo;
  }

  private Map<String, String> headers() {
    return Map.of("Content-Type", "application/json");
  }
}
