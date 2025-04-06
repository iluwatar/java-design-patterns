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
package com.iluwatar.poison.pill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/** ProducerTest */
class ProducerTest {

  @Test
  void testSend() throws Exception {
    final var publishPoint = mock(MqPublishPoint.class);
    final var producer = new Producer("producer", publishPoint);
    verifyNoMoreInteractions(publishPoint);

    producer.send("Hello!");

    final var messageCaptor = ArgumentCaptor.forClass(Message.class);
    verify(publishPoint).put(messageCaptor.capture());

    final var message = messageCaptor.getValue();
    assertNotNull(message);
    assertEquals("producer", message.getHeader(Message.Headers.SENDER));
    assertNotNull(message.getHeader(Message.Headers.DATE));
    assertEquals("Hello!", message.getBody());

    verifyNoMoreInteractions(publishPoint);
  }

  @Test
  void testStop() throws Exception {
    final var publishPoint = mock(MqPublishPoint.class);
    final var producer = new Producer("producer", publishPoint);
    verifyNoMoreInteractions(publishPoint);

    producer.stop();
    verify(publishPoint).put(eq(Message.POISON_PILL));

    try {
      producer.send("Hello!");
      fail("Expected 'IllegalStateException' at this point, since the producer has stopped!");
    } catch (IllegalStateException e) {
      assertNotNull(e);
      assertNotNull(e.getMessage());
      assertEquals(
          "Producer Hello! was stopped and fail to deliver requested message [producer].",
          e.getMessage());
    }

    verifyNoMoreInteractions(publishPoint);
  }
}
