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
package com.iluwatar.payment.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * With the Microservices pattern, a request often travels through multiple different microservices.
 * Tracking the entire request flow across these services can be challenging, especially when trying
 * to diagnose performance issues or failures. Distributed tracing addresses this challenge by
 * providing end-to-end visibility into the lifecycle of a request as it passes through various
 * microservices.
 *
 * <p>The intent of the Distributed Tracing pattern is to trace a request across different
 * microservices, collecting detailed timing data and logs that help in understanding the flow,
 * performance bottlenecks, and failure points in a distributed system. Each microservice involved
 * in the request contributes to the tracing data, creating a comprehensive view of the request's
 * journey.
 *
 * <p>This implementation demonstrates distributed tracing in a microservices architecture for an
 * e-commerce platform. When a customer places an order, the OrderService interacts with
 * both the PaymentService to process the payment and the ProductService to check the
 * product inventory. Tracing logs are generated for each interaction, and these logs can be
 * visualized using Zipkin.
 *
 * <p>To run Zipkin and view the tracing logs, you can use the following Docker command:
 *
 * <pre>
 * {@code docker run -d -p 9411:9411 --name zipkin openzipkin/zipkin }
 * </pre>
 *
 * <p>Start Zipkin with the command above. Once Zipkin is running, you can
 * access the Zipkin UI at <a href="http://localhost:9411">http://localhost:9411</a>
 * to view the tracing logs and analyze the request flows across your microservices.
 *
 * <p>To place an order and generate tracing data, you can use the following curl command:
 *
 * <pre>
 * {@code curl -X POST http://localhost:30300/order -H "Content-Type: application/json" -d '{"orderId": "123"}' }
 * </pre>
 *
 * <p>This command sends a POST request to create an order, which will trigger interactions with the
 * payment and product microservices, generating tracing logs that can be viewed in Zipkin.
 *
 */
@SpringBootApplication
public class Main {
  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
}
