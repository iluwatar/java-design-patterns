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

package com.iluwatar.circuitbreaker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Monitoring Service test
 */
class DelayedRemoteServiceTest {

  /**
   * Testing immediate response of the delayed service.
   *
   * @throws RemoteServiceException
   */
  @Test
  void testDefaultConstructor() throws RemoteServiceException {
    Assertions.assertThrows(RemoteServiceException.class, () -> {
      var obj = new DelayedRemoteService();
      obj.call();
    });
  }

  /**
   * Testing server started in past (2 seconds ago) and with a simulated delay of 1 second.
   *
   * @throws RemoteServiceException
   */
  @Test
  public void testParameterizedConstructor() throws RemoteServiceException {
      var obj = new DelayedRemoteService(System.nanoTime()-2000*1000*1000,1);
      assertEquals("Delayed service is working",obj.call());
  }
}
