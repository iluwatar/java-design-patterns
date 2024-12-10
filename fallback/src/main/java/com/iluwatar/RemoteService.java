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
package com.iluwatar;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * RemoteService implementation that makes HTTP calls to an external API.
 * This service acts as the primary service in the fallback pattern.
 */
public class RemoteService implements Service {
  private final String apiUrl;
  private final HttpClient httpClient;
  private static final int TIMEOUT_SECONDS = 2;

  public RemoteService(String apiUrl, HttpClient httpClient) {
    this.apiUrl = apiUrl;
    this.httpClient = httpClient;
  }

  @Override
  public String getData() throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(apiUrl))
        .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
        .GET()
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    
    if (response.statusCode() != 200) {
      throw new Exception("Remote service failed with status code: " + response.statusCode());
    }
    
    return response.body();
  }
}
