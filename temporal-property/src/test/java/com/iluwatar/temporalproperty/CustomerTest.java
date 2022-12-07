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
package com.iluwatar.temporalproperty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {
  private Customer martin;
  private String franklin = "961 Franklin St";
  private String worcester = "88 Worcester St";

  private SimpleDate jul1 = new SimpleDate(1996, 7, 1);
  private SimpleDate jul15 = new SimpleDate(1996, 7, 15);

  @BeforeEach
  public void setUp () {
    // putting different addresses as time changes
    SimpleDate.setToday(new SimpleDate(1996,1,1));
    martin = new Customer (15, "Martin");
    martin.putAddress(worcester, new SimpleDate(1994, 3, 1));
    SimpleDate.setToday(new SimpleDate(1996,8,10));
    martin.putAddress(franklin, new SimpleDate(1996, 7, 4));
    SimpleDate.setToday(new SimpleDate(2000,9,11));
  }

  @Test
  public void locationAtTime(){
    assertEquals(worcester, martin.getAddress(jul1));
    assertEquals(franklin, martin.getAddress(jul15));
  }

  @Test public void locationToday(){
    SimpleDate.setToday(new SimpleDate(1994, 4, 5));
    assertEquals(worcester, martin.getAddress());
    SimpleDate.setToday(new SimpleDate(1998, 2, 3));
    assertEquals(franklin, martin.getAddress());
  }

  @Test public void idGet(){
    assertEquals(15, martin.getId());
  }

  @Test public void nameGetSet(){
    assertEquals("Martin", martin.getName());
    martin.setName("John");
    assertEquals("John", martin.getName());
  }



}
