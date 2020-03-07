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

package com.iluwatar.memento;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 12/20/15 - 10:08 AM
 *
 * @author Jeroen Meulemeester
 */
public class StarTest {

  /**
   * Verify the stages of a dying sun, without going back in time
   */
  @Test
  public void testTimePasses() {
    final Star star = new Star(StarType.SUN, 1, 2);
    assertEquals("sun age: 1 years mass: 2 tons", star.toString());

    star.timePasses();
    assertEquals("red giant age: 2 years mass: 16 tons", star.toString());

    star.timePasses();
    assertEquals("white dwarf age: 4 years mass: 128 tons", star.toString());

    star.timePasses();
    assertEquals("supernova age: 8 years mass: 1024 tons", star.toString());

    star.timePasses();
    assertEquals("dead star age: 16 years mass: 8192 tons", star.toString());

    star.timePasses();
    assertEquals("dead star age: 64 years mass: 0 tons", star.toString());

    star.timePasses();
    assertEquals("dead star age: 256 years mass: 0 tons", star.toString());
  }

  /**
   * Verify some stage of a dying sun, but go back in time to test the memento
   */
  @Test
  public void testSetMemento() {
    final Star star = new Star(StarType.SUN, 1, 2);
    final StarMemento firstMemento = star.getMemento();
    assertEquals("sun age: 1 years mass: 2 tons", star.toString());

    star.timePasses();
    final StarMemento secondMemento = star.getMemento();
    assertEquals("red giant age: 2 years mass: 16 tons", star.toString());

    star.timePasses();
    final StarMemento thirdMemento = star.getMemento();
    assertEquals("white dwarf age: 4 years mass: 128 tons", star.toString());

    star.timePasses();
    assertEquals("supernova age: 8 years mass: 1024 tons", star.toString());

    star.setMemento(thirdMemento);
    assertEquals("white dwarf age: 4 years mass: 128 tons", star.toString());

    star.timePasses();
    assertEquals("supernova age: 8 years mass: 1024 tons", star.toString());

    star.setMemento(secondMemento);
    assertEquals("red giant age: 2 years mass: 16 tons", star.toString());

    star.setMemento(firstMemento);
    assertEquals("sun age: 1 years mass: 2 tons", star.toString());

  }

}
