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

package com.iluwatar.lazy.loading;

import java.lang.reflect.Field;
import java.util.function.Supplier;

/**
 * Date: 12/19/15 - 12:27 PM
 *
 * @author Jeroen Meulemeester
 */
public class Java8HolderTest extends AbstractHolderTest {

  private final Java8Holder holder = new Java8Holder();


  @Override
  Heavy getInternalHeavyValue() throws Exception {
    final var holderField = Java8Holder.class.getDeclaredField("heavy");
    holderField.setAccessible(true);

    final var supplier = (Supplier<Heavy>) holderField.get(this.holder);
    final var supplierClass = supplier.getClass();

    // This is a little fishy, but I don't know another way to test this:
    // The lazy holder is at first a lambda, but gets replaced with a new supplier after loading ...
    if (supplierClass.isLocalClass()) {
      final var instanceField = supplierClass.getDeclaredField("heavyInstance");
      instanceField.setAccessible(true);
      return (Heavy) instanceField.get(supplier);
    } else {
      return null;
    }
  }

  @Override
  Heavy getHeavy() throws Exception {
    return holder.getHeavy();
  }

}