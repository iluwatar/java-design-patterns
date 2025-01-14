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
package com.iluwatar.sessionserver;

import static java.lang.Thread.State.TIMED_WAITING;
import static java.lang.Thread.State.WAITING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockitoAnnotations;

/**
 * LoginHandlerTest.
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {

  /**
   * Start App before tests
   * @throws IOException
   */

  @BeforeAll
  public static void init() throws IOException {
    App.main(new String [] {});
  }

  /**
   * Setup tests.
   */

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  /**
   * Run the Start state test first
   * Checks that the session expiration task is waiting when the app is first started
   */

  @Test
  @Order(1)
  public void expirationTaskStartStateTest() {
    //assert
    LOGGER.info("Expiration Task Status: "+String.valueOf(App.getExpirationTaskState()));
    assertEquals(App.getExpirationTaskState(),WAITING);
  }


  /**
   * Run the wake state test second
   * Test whether expiration Task is currently sleeping or not (should sleep when woken)
   */

  @Test
  @Order(2)
  public void expirationTaskWakeStateTest() throws InterruptedException {
    App.expirationTaskWake();
    Thread.sleep(200); // Wait until sessionExpirationTask is sleeping
    LOGGER.info("Expiration Task Status: "+String.valueOf(App.getExpirationTaskState()));
    assertEquals(App.getExpirationTaskState(),TIMED_WAITING);
  }

}