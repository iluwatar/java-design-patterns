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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * HeroStat is a value object.
 *
 * @see <a href="http://docs.oracle.com/javase/8/docs/api/java/lang/doc-files/ValueBased.html">
 *     http://docs.oracle.com/javase/8/docs/api/java/lang/doc-files/ValueBased.html
 *     </a>
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class HeroStat {

  // Stats for a hero

  private final int strength;
  private final int intelligence;
  private final int luck;

  // Static factory method to create new instances.
  public static HeroStat valueOf(int strength, int intelligence, int luck) {
    return new HeroStat(strength, intelligence, luck);
  }

  // The clone() method should not be public. Just don't override it.

}
