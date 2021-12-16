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

package com.iluwatar.fanout.fanin;

import java.security.SecureRandom;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Squares the number with a little timeout to give impression of long running process that return
 * at different times.
 */
@Slf4j
@AllArgsConstructor
public class SquareNumberRequest {

  private final Long number;

  /**
   * Squares the number with a little timeout to give impression of long running process that return
   * at different times.
   * @param consumer callback class that takes the result after the delay.
   * */
  public void delayedSquaring(final Consumer consumer) {

    var minTimeOut = 5000L;

    SecureRandom secureRandom = new SecureRandom();
    var randomTimeOut = secureRandom.nextInt(2000);

    try {
      // this will make the thread sleep from 5-7s.
      Thread.sleep(minTimeOut + randomTimeOut);
    } catch (InterruptedException e) {
      LOGGER.error("Exception while sleep ", e);
      Thread.currentThread().interrupt();
    } finally {
      consumer.add(number * number);
    }
  }
}
