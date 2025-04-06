/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.clientsideuicomposition;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * ClientSideIntegrator class simulates the client-side integration layer that dynamically assembles
 * various frontend components into a cohesive user interface.
 */
@Slf4j
public class ClientSideIntegrator {

  private final ApiGateway apiGateway;

  /**
   * Constructor that accepts an instance of ApiGateway to handle dynamic routing.
   *
   * @param apiGateway the gateway that routes requests to different frontend components
   */
  public ClientSideIntegrator(ApiGateway apiGateway) {
    this.apiGateway = apiGateway;
  }

  /**
   * Composes the user interface dynamically by fetching data from different frontend components
   * based on provided parameters.
   *
   * @param path the route of the frontend component
   * @param params a map of dynamic parameters to influence the data fetching
   */
  public void composeUi(String path, Map<String, String> params) {
    // Fetch data dynamically based on the route and parameters
    String data = apiGateway.handleRequest(path, params);
    LOGGER.info("Composed UI Component for path '" + path + "':");
    LOGGER.info(data);
  }
}
