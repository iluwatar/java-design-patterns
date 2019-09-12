/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Message;
import com.iluwatar.leaderelection.MessageType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * RingMessage Test
 */
public class RingMessageTest {

  @Test
  public void testGetType() {
    final RingMessage ringMessage = new RingMessage(MessageType.HEARTBEAT, "");
    assertEquals(ringMessage.getType(), MessageType.HEARTBEAT);
  }

  @Test
  public void testSetType() {
    final RingMessage ringMessage = new RingMessage(MessageType.HEARTBEAT, "");
    ringMessage.setType(MessageType.ELECTION);
    assertEquals(ringMessage.getType(), MessageType.ELECTION);
  }

  @Test
  public void testGetContent() {
    final RingMessage ringMessage = new RingMessage(MessageType.HEARTBEAT, "test");
    assertEquals(ringMessage.getContent(), "test");
  }

  @Test
  public void testSetContent() {
    final RingMessage ringMessage = new RingMessage(MessageType.HEARTBEAT, "");
    ringMessage.setContent("test");
    assertEquals(ringMessage.getContent(), "test");
  }
}
