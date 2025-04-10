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
package com.iluwatar.backpressure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SubscriberTest {

  @RegisterExtension public LoggerExtension loggerExtension = new LoggerExtension();

  @Test
  public void testSubscribe() throws InterruptedException {

    Subscriber sub = new Subscriber();
    Publisher.publish(1, 8, 100).subscribe(sub);

    App.latch = new CountDownLatch(1);
    App.latch.await();

    String result = String.join(",", loggerExtension.getFormattedMessages());
    assertTrue(
        result.endsWith(
            "onSubscribe(FluxConcatMapNoPrefetch."
                + "FluxConcatMapNoPrefetchSubscriber),request(10),onNext(1),process(1),onNext(2),"
                + "process(2),onNext(3),process(3),onNext(4),process(4),onNext(5),process(5),request(5),"
                + "onNext(6),process(6),onNext(7),process(7),onNext(8),process(8),onComplete()"));
  }
}
