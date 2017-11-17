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
package com.iluwatar.prototype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Date: 12/28/15 - 8:45 PM
 * @param <P> Prototype
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class PrototypeTest<P extends Prototype> {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(
            new Object[]{new OrcBeast(), "Orcish wolf"},
            new Object[]{new OrcMage(), "Orcish mage"},
            new Object[]{new OrcWarlord(), "Orcish warlord"},
            new Object[]{new ElfBeast(), "Elven eagle"},
            new Object[]{new ElfMage(), "Elven mage"},
            new Object[]{new ElfWarlord(), "Elven warlord"}
    );
  }

  /**
   * The tested prototype instance
   */
  private final P testedPrototype;

  /**
   * The expected {@link Prototype#toString()} value
   */
  private final String expectedToString;

  /**
   * Create a new test instance, using the given test object and expected value
   *
   * @param testedPrototype  The tested prototype instance
   * @param expectedToString The expected {@link Prototype#toString()} value
   */
  public PrototypeTest(final P testedPrototype, final String expectedToString) {
    this.expectedToString = expectedToString;
    this.testedPrototype = testedPrototype;
  }

  @Test
  public void testPrototype() throws Exception {
    assertEquals(this.expectedToString, this.testedPrototype.toString());

    final Object clone = this.testedPrototype.clone();
    assertNotNull(clone);
    assertNotSame(clone, this.testedPrototype);
    assertSame(this.testedPrototype.getClass(), clone.getClass());
  }

}
