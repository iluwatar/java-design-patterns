/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import static com.iluwatar.poison.pill.Message.Headers;
import static com.iluwatar.poison.pill.Message.POISON_PILL;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Date: 12/27/15 - 10:30 PM
 *
 * @author Jeroen Meulemeester
 */
public class PoisonMessageTest {

  @Test
  public void testAddHeader() {
    assertThrows(UnsupportedOperationException.class, () -> {
      POISON_PILL.addHeader(Headers.SENDER, "sender");
    });
  }

  @Test
  public void testGetHeader() {
    assertThrows(UnsupportedOperationException.class, () -> {
      POISON_PILL.getHeader(Headers.SENDER);
    });
  }

  @Test
  public void testGetHeaders() {
    assertThrows(UnsupportedOperationException.class, POISON_PILL::getHeaders);
  }

  @Test
  public void testSetBody() {
    assertThrows(UnsupportedOperationException.class, () -> {
      POISON_PILL.setBody("Test message.");
    });
  }

  @Test
  public void testGetBody() {
    assertThrows(UnsupportedOperationException.class, POISON_PILL::getBody);
  }

}
