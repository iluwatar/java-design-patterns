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
package com.iluwatar.poison.pill;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Date: 12/27/15 - 10:25 PM
 *
 * @author Jeroen Meulemeester
 */
public class SimpleMessageTest {

  @Test
  public void testGetHeaders() {
    final SimpleMessage message = new SimpleMessage();
    assertNotNull(message.getHeaders());
    assertTrue(message.getHeaders().isEmpty());

    final String senderName = "test";
    message.addHeader(Message.Headers.SENDER, senderName);
    assertNotNull(message.getHeaders());
    assertFalse(message.getHeaders().isEmpty());
    assertEquals(senderName, message.getHeaders().get(Message.Headers.SENDER));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testUnModifiableHeaders() {
    final SimpleMessage message = new SimpleMessage();
    final Map<Message.Headers, String> headers = message.getHeaders();
    headers.put(Message.Headers.SENDER, "test");
  }


}