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
package com.iluwatar.effectivity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EffectivityTests {
  Person duke;
  Company india = new Company("india");
  Company peninsular = new Company("peninsular");
  Company dublin = new Company("dublin");

  @BeforeEach
  public void setUp() {
    duke = new Person("Duke");
    duke.addEmployment(india, new SimpleDate(1999,12,1));
    duke.addEmployment(peninsular, new SimpleDate(2000,4,1));
    duke.employments()[0].end(new SimpleDate (2000,5,1));
  }

  @Test
  public void testAdditive() {
    assertEquals(2, duke.employments().length);
    Employment actual = null;
    for (int i = 0; i < duke.employments().length; i++) {
      if (duke.employments()[i].isEffectiveOn(new SimpleDate(2000,6,1))) {
        actual = duke.employments()[i];
        break;
      }
    }
    assertNotNull(actual);
    assertEquals(peninsular, actual.company());
    assertEquals("peninsular", actual.company().toString());
  }

  @Test
  public void testRetro() {
    duke.employments()[1].setEffectivity(DateRange.startingOn(new SimpleDate(2000,6,1)));
    duke.addEmployment(new Employment(dublin, new DateRange(new SimpleDate(2000,5,1), new SimpleDate(2000,5,31))));
    Employment april = null;
    for (int i = 0; i < duke.employments().length; i++) {
      if (duke.employments()[i].isEffectiveOn(new SimpleDate(2000,4,10))) {
        april = duke.employments()[i];
        break;
      }
    }
    assertNotNull(april);
    assertEquals(india, april.company());
    Employment may = null;
    for (int i = 0; i < duke.employments().length; i++) {
      if (duke.employments()[i].isEffectiveOn(new SimpleDate(2000,5,10))) {
        may = duke.employments()[i];
        break;
      }
    }
    assertNotNull(may);
    assertEquals(dublin, may.company());
  }

  @Test
  public void correctEmploymentEffectiveDates(){
    Employment employment = new Employment(dublin, new SimpleDate(1,1,1));
    employment.end(new SimpleDate(2,2,2));
    assertFalse(employment.isEffectiveOn(new SimpleDate(0,12,31)));
    assertFalse(employment.isEffectiveOn(new SimpleDate(2,2,3)));
    assertTrue(employment.isEffectiveOn(new SimpleDate(1,1,1)));
    assertTrue(employment.isEffectiveOn(new SimpleDate(1,2,3)));
    assertTrue(employment.isEffectiveOn(new SimpleDate(2,2,2)));
  }
}
