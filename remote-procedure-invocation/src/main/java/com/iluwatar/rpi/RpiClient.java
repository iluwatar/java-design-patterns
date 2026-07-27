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
package com.iluwatar.rpi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

/**
 * The client-side component of the RPI pattern. Uses {@link RestTemplate} to make a synchronous
 * HTTP GET request to the remote {@link RpiService} and returns the response. This demonstrates the
 * core of RPI — a client blocking on a remote call and waiting for the result.
 */
@Slf4j
public class RpiClient {

  private final RestTemplate restTemplate;
  private final String baseUrl;

  /**
   * Creates a new RpiClient.
   *
   * @param restTemplate the RestTemplate used to perform HTTP calls
   * @param baseUrl the base URL of the remote service (e.g. "http://localhost:8080")
   */
  public RpiClient(RestTemplate restTemplate, String baseUrl) {
    this.restTemplate = restTemplate;
    this.baseUrl = baseUrl;
  }

  /**
   * Invokes the remote greeting service with the given name.
   *
   * @param name the name to send to the remote service
   * @return the greeting response from the service
   */
  public String greet(String name) {
    var url = baseUrl + "/greet?name=" + name;
    LOGGER.info("Calling remote service: {}", url);
    var response = restTemplate.getForObject(url, String.class);
    LOGGER.info("Received response: {}", response);
    return response;
  }
}