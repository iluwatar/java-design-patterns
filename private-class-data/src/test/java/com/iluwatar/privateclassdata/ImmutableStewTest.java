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
package com.iluwatar.privateclassdata;

import com.iluwatar.privateclassdata.utils.InMemoryAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/27/15 - 10:46 PM
 *
 * @author Jeroen Meulemeester
 */
public class ImmutableStewTest {

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender();
  }

  @After
  public void tearDown() {
    appender.stop();
  }

  /**
   * Verify if mixing the stew doesn't change the internal state
   */
  @Test
  public void testMix() {
    final Stew stew = new Stew(1, 2, 3, 4);
    final String expectedMessage = "Mixing the stew we find: 1 potatoes, 2 carrots, 3 meat and 4 peppers";

    for (int i = 0; i < 20; i++) {
      stew.mix();
      assertEquals(expectedMessage, appender.getLastMessage());
    }

    assertEquals(20, appender.getLogSize());
  }

  /**
   * Verify if tasting the stew actually removes one of each ingredient
   */
  @Test
  public void testDrink() {
    final Stew stew = new Stew(1, 2, 3, 4);
    stew.mix();

    assertEquals("Mixing the stew we find: 1 potatoes, 2 carrots, 3 meat and 4 peppers",  appender.getLastMessage());

    stew.taste();
    assertEquals("Tasting the stew",  appender.getLastMessage());

    stew.mix();
    assertEquals("Mixing the stew we find: 0 potatoes, 1 carrots, 2 meat and 3 peppers",  appender.getLastMessage());
  }
}
