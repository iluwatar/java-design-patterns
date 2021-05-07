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

package com.iluwatar.api.gateway;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * An adapter to communicate with the Price microservice.
 */
@Slf4j
@Component
public class PriceClientImpl implements PriceClient {

  /**
   * Makes a simple HTTP Get request to the Price microservice.
   *
   * @return The price of the product
   */
  @Override
  public String getPrice() {
    var httpClient = HttpClient.newHttpClient();
    var httpGet = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://localhost:50006/price"))
        .build();

    try {
      LOGGER.info("Sending request to fetch price info");
      var httpResponse = httpClient.send(httpGet, BodyHandlers.ofString());
      logResponse(httpResponse);
      return httpResponse.body();
    } catch (IOException e) {
      LOGGER.error("Failure occurred while getting price info", e);
    } catch (InterruptedException e) {
      LOGGER.error("Failure occurred while getting price info", e);
      Thread.currentThread().interrupt();
    }

    return null;
  }

  private void logResponse(HttpResponse<String> httpResponse) {
    if (isSuccessResponse(httpResponse.statusCode())) {
      LOGGER.info("Price info received successfully");
    } else {
      LOGGER.warn("Price info request failed");
    }
  }

  private boolean isSuccessResponse(int responseCode) {
    return responseCode >= 200 && responseCode <= 299;
  }
}
