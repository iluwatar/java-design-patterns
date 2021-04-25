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

package com.iluwatar.identityfield;


import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * The type App test.
 */
public class AppTest {
  /**
   * App test.
   */
  @Test
  public void AppTest() {
    List<Person> pearsonList = new LinkedList<>();

    for (int i = 0; i < 5; i++) {
      Person person = new Person(i, "pearson");
      String t = "pearson" + Integer.toString(i);
      person.setName(t);
      pearsonList.add(person);
    }

    assertEquals(0, pearsonList.get(0).getId());
    assertEquals("pearson0", pearsonList.get(0).getName());
    assertEquals(1, pearsonList.get(1).getId());
    assertEquals("pearson1", pearsonList.get(1).getName());
    assertEquals(2, pearsonList.get(2).getId());
    assertEquals("pearson2", pearsonList.get(2).getName());
    assertEquals(3, pearsonList.get(3).getId());
    assertEquals("pearson3", pearsonList.get(3).getName());
    assertEquals(4, pearsonList.get(4).getId());
    assertEquals("pearson4", pearsonList.get(4).getName());


  }
}
