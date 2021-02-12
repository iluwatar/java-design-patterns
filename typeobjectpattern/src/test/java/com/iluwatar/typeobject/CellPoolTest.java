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

package com.iluwatar.typeobject;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Hashtable;
import org.junit.jupiter.api.Test;

/**
 * The CellPoolTest class tests the methods in the {@link CellPool} class.
 */

class CellPoolTest {

  @Test
  void assignRandomCandyTypesTest() {
    var cp = new CellPool(10);
    var ht = new Hashtable<String, Boolean>();
    var parentTypes = 0;
    for (var i = 0; i < cp.randomCode.length; i++) {
      ht.putIfAbsent(cp.randomCode[i].name, true);
      if (cp.randomCode[i].name.equals("fruit") || cp.randomCode[i].name.equals("candy")) {
        parentTypes++;
      }
    }
    assertTrue(ht.size() == 5 && parentTypes == 0);
  }

}
