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
package com.iluwatar.ambassador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

/**
 * A remote legacy application represented by a Singleton implementation.
 */
public class RemoteService implements RemoteServiceInterface {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoteService.class);
  private static RemoteService service = null;

  static synchronized RemoteService getRemoteService() {
    if (service == null) {
      service = new RemoteService();
    }
    return service;
  }

  private RemoteService() {}

  /**
   * Remote function takes a value and multiplies it by 10 taking a random amount of time.
   * Will sometimes return -1. This imitates connectivity issues a client might have to account for.
   * @param value integer value to be multiplied.
   * @return if waitTime is more than 200ms, it returns value * 10, otherwise -1.
   */
  @Override
  public long doRemoteFunction(int value) {

    long waitTime = (long) Math.floor(Math.random() * 1000);

    try {
      sleep(waitTime);
    } catch (InterruptedException e) {
      LOGGER.error("Thread sleep state interrupted", e);
    }
    return waitTime >= 200 ? value * 10 : -1;
  }
}
