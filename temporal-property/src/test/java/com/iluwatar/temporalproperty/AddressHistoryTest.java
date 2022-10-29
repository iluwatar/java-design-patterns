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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressHistoryTest {
  AddressHistory history;

  private static final String franklin = "961 Franklin St";
  private static final String worcester = "88 Worcester St";


  @BeforeEach
  public void setup(){
    SimpleDate.setToday(new SimpleDate(1996,1,1));
    history = new AddressHistory();
    history.put(new SimpleDate(1994, 3, 1), worcester);
    SimpleDate.setToday(new SimpleDate(1996,8,10));
    history.put(new SimpleDate(1996, 7, 4), franklin);
  }

  @Test
  public void getAddressEqual(){
    assertEquals(worcester, history.get(new SimpleDate(1994, 3, 1)));
    assertEquals(franklin, history.get(new SimpleDate(1996, 7, 4)));
  }

  @Test
  public void throwOnTooEarly(){
    assertThrows(IllegalStateException.class,
            () -> history.get(new SimpleDate(1993, 5, 7)));
  }

  @Test
  public void inbetweenEntriesAddress(){
    assertEquals(worcester, history.get(new SimpleDate(1995, 2, 14)));
    assertEquals(franklin, history.get(new SimpleDate(1997, 3, 6)));
  }

}
