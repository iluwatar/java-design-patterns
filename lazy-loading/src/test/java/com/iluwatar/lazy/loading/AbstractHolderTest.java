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

package com.iluwatar.lazy.loading;

import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTimeout;

/**
 * Date: 12/19/15 - 11:58 AM
 *
 * @author Jeroen Meulemeester
 */
public abstract class AbstractHolderTest {

  /**
   * Get the internal state of the holder value
   *
   * @return The internal value
   */
  abstract Heavy getInternalHeavyValue() throws Exception;

  /**
   * Request a lazy loaded {@link Heavy} object from the holder.
   *
   * @return The lazy loaded {@link Heavy} object
   */
  abstract Heavy getHeavy() throws Exception;

  /**
   * This test shows that the heavy field is not instantiated until the method getHeavy is called
   */
  @Test
  public void testGetHeavy() throws Exception {
    assertTimeout(ofMillis(3000), () -> {
      assertNull(getInternalHeavyValue());
      assertNotNull(getHeavy());
      assertNotNull(getInternalHeavyValue());
      assertSame(getHeavy(), getInternalHeavyValue());
    });
  }

}
