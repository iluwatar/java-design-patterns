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
package com.iluwatar.typeobject

// ABOUTME: Tests the CellPool class, verifying that random candy type assignment works correctly.
// ABOUTME: Validates that generic parent types are excluded from the random candy code array.

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Hashtable

/** The CellPoolTest class tests the methods in the [CellPool] class. */
class CellPoolTest {

  @Test
  fun assignRandomCandyTypesTest() {
    val cp = CellPool(10)
    val ht = Hashtable<String, Boolean>()
    var parentTypes = 0
    for (i in cp.randomCode.indices) {
      ht.putIfAbsent(cp.randomCode[i].name, true)
      if (cp.randomCode[i].name == "fruit" || cp.randomCode[i].name == "candy") {
        parentTypes++
      }
    }
    assertTrue(ht.size == 5 && parentTypes == 0)
  }
}
