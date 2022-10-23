/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.auditlog;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleDateTest {

  @BeforeEach
  public void setup(){
    SimpleDate.setToday(new SimpleDate(0,0,0));
  }

  @Test
  public void todaySetCorrectly() {
    SimpleDate newToday = new SimpleDate(1,2,3);

    SimpleDate.setToday(newToday);
    assertEquals(newToday, SimpleDate.getToday());
  }

  @Test
  public void dateComparison(){
    SimpleDate less = new SimpleDate(1,2,3);
    SimpleDate middle = new SimpleDate(2, 3, 1);
    SimpleDate equalMiddle = new SimpleDate(2, 3, 1);
    SimpleDate more = new SimpleDate(3, 1, 2);

    assertEquals(0, middle.compareTo(equalMiddle));

    assertEquals(-1, less.compareTo(middle));
    assertEquals(-1, less.compareTo(more));

    assertEquals(-1, middle.compareTo(more));
    assertEquals(1, middle.compareTo(less));

    assertEquals(1, more.compareTo(less));
    assertEquals(1, more.compareTo(middle));

    assertEquals(1, new SimpleDate(1,0,0).compareTo(new SimpleDate(0,0,0)));
    assertEquals(-1, new SimpleDate(0,0,0).compareTo(new SimpleDate(1,0,0)));

    assertEquals(1, new SimpleDate(0,1,0).compareTo(new SimpleDate(0,0,0)));
    assertEquals(-1, new SimpleDate(0,0,0).compareTo(new SimpleDate(0,1,0)));

    assertEquals(1, new SimpleDate(0,0,1).compareTo(new SimpleDate(0,0,0)));
    assertEquals(-1, new SimpleDate(0,0,0).compareTo(new SimpleDate(0,0,1)));

    assertNotEquals(null, less);
  }

  @Test
  public void correctString(){
    SimpleDate jul1 = new SimpleDate(1932, 7, 1);
    assertEquals("1932, 7, 1", jul1.toString());
    SimpleDate aug3 = new SimpleDate(2014, 8, 3);
    assertEquals("2014, 8, 3", aug3.toString());
  }
}