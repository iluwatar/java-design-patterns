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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Tests for the Remote Procedure Invocation pattern.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;

  /**
   * Verify that the application starts without exception.
   */
  @Test
  void shouldExecuteApplicationWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[] {"test"}));
  }

  /**
   * Verify that the /greet endpoint returns a greeting with default name.
   */
  @Test
  void shouldReturnDefaultGreeting() {
    var response =
        testRestTemplate.getForObject("http://localhost:" + port + "/greet", String.class);
    assertEquals("Hello, World!", response);
  }

  /**
   * Verify that the /greet endpoint returns a greeting with a custom name.
   */
  @Test
  void shouldReturnCustomGreeting() {
    var response =
        testRestTemplate.getForObject(
            "http://localhost:" + port + "/greet?name=Java", String.class);
    assertEquals("Hello, Java!", response);
  }

  /**
   * Verify that the RpiClient correctly calls the service and receives a response.
   */
  @Test
  void shouldClientReceiveGreetingFromService() {
    var client =
        new RpiClient(
            testRestTemplate.getRestTemplate(), "http://localhost:" + port);
    var result = client.greet("RPI");
    assertEquals("Hello, RPI!", result);
  }
}