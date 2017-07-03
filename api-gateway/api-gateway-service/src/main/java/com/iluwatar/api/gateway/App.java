/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * With the Microservices pattern, a client may need data from multiple different microservices.
 * If the client called each microservice directly, that could contribute to longer load times,
 * since the client would have to make a network request for each microservice called. Moreover,
 * having the client call each microservice directly ties the client to that microservice - if the
 * internal implementations of the microservices change (for example, if two microservices are
 * combined sometime in the future) or if the location (host and port) of a microservice changes,
 * then every client that makes use of those microservices must be updated.
 *
 * <p>
 * The intent of the API Gateway pattern is to alleviate some of these issues. In the API Gateway
 * pattern, an additional entity (the API Gateway) is placed between the client and the
 * microservices. The job of the API Gateway is to aggregate the calls to the microservices.
 * Rather than the client calling each microservice individually, the client calls the API Gateway
 * a single time. The API Gateway then calls each of the microservices that the client needs.
 *
 * <p>
 * This implementation shows what the API Gateway pattern could look like for an e-commerce site.
 * The {@link ApiGateway} makes calls to the Image and Price microservices using the
 * {@link ImageClientImpl} and {@link PriceClientImpl} respectively. Customers viewing the site on a
 * desktop device can see both price information and an image of a product, so the {@link ApiGateway}
 * calls both of the microservices and aggregates the data in the {@link DesktopProduct} model.
 * However, mobile users only see price information; they do not see a product image. For mobile
 * users, the {@link ApiGateway} only retrieves price information, which it uses to populate the
 * {@link MobileProduct}.
 */
@SpringBootApplication
public class App {

  /**
   * Program entry point
   *
   * @param args
   *          command line args
   */
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
