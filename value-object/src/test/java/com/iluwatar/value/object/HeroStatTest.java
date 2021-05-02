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

package com.iluwatar.value.object;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import com.google.common.testing.EqualsTester;
import org.junit.jupiter.api.Test;

/**
 * Unit test for HeroStat.
 */
class HeroStatTest {

  /**
   * Tester for equals() and hashCode() methods of a class. Using guava's EqualsTester.
   *
   * @see <a href="http://static.javadoc.io/com.google.guava/guava-testlib/19.0/com/google/common/testing/EqualsTester.html">
   * http://static.javadoc.io/com.google.guava/guava-testlib/19.0/com/google/common/testing/EqualsTester.html
   * </a>
   */
  @Test
  void testEquals() {
    var heroStatA = HeroStat.valueOf(3, 9, 2);
    var heroStatB = HeroStat.valueOf(3, 9, 2);
    new EqualsTester().addEqualityGroup(heroStatA, heroStatB).testEquals();
  }

  /**
   * The toString() for two equal values must be the same. For two non-equal values it must be
   * different.
   */
  @Test
  void testToString() {
    var heroStatA = HeroStat.valueOf(3, 9, 2);
    var heroStatB = HeroStat.valueOf(3, 9, 2);
    var heroStatC = HeroStat.valueOf(3, 9, 8);

    assertThat(heroStatA.toString(), is(heroStatB.toString()));
    assertThat(heroStatA.toString(), is(not(heroStatC.toString())));
  }

}
