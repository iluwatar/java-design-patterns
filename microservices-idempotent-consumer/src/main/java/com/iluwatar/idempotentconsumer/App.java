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
package com.iluwatar.idempotentconsumer;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The main entry point for the idempotent-consumer application.
 * This application demonstrates the use of the Idempotent Consumer
 * pattern which ensures that a message is processed exactly once
 * in scenarios where the same message can be delivered multiple times.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Idempotence">Idempotence (Wikipedia)</a>
 * @see <a href="https://camel.apache.org/components/latest/eips/idempotentConsumer-eip.html">Idempotent Consumer Pattern (Apache Camel)</a>
 */
@SpringBootApplication
@Slf4j
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
  /**
   * The starting point of the CommandLineRunner
   * where the main program is run.
   *
   * @param requestService idempotent request service
   * @param requestRepository request jpa repository
   */
  @Bean
  public CommandLineRunner run(RequestService requestService, RequestRepository requestRepository) {
    return args -> {
      Request req = requestService.create(UUID.randomUUID());
      requestService.create(req.getUuid());
      requestService.create(req.getUuid());
      LOGGER.info("Nb of requests : {}", requestRepository.count()); // 1, processRequest is idempotent
      req = requestService.start(req.getUuid());
      try {
        req = requestService.start(req.getUuid());
      } catch (InvalidNextStateException ex) {
        LOGGER.error("Cannot start request twice!");
      }
      req = requestService.complete(req.getUuid());
      LOGGER.info("Request: {}", req);
    };
  }
}

