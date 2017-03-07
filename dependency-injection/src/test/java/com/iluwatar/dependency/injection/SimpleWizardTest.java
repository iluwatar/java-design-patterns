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
package com.iluwatar.dependency.injection;

import com.iluwatar.dependency.injection.utils.InMemoryAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/10/15 - 8:26 PM
 *
 * @author Jeroen Meulemeester
 */
public class SimpleWizardTest {

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender(Tobacco.class);
  }

  @After
  public void tearDown() {
    appender.stop();
  }

  /**
   * Test if the {@link SimpleWizard} does the only thing it can do: Smoke it's {@link
   * OldTobyTobacco}
   */
  @Test
  public void testSmoke() {
    final SimpleWizard simpleWizard = new SimpleWizard();
    simpleWizard.smoke();
    assertEquals("SimpleWizard smoking OldTobyTobacco", appender.getLastMessage());
    assertEquals(1, appender.getLogSize());
  }

}
