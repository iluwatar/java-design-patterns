/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.visitor;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.function.Function;
import org.junit.Test;

/**
 * Date: 12/30/15 - 18:59 PM
 * Test related to Units
 * @param <U> Type of Unit
 * @author Jeroen Meulemeester
 */
public abstract class UnitTest<U extends Unit> {

  /**
   * Factory to create new instances of the tested unit
   */
  private final Function<Unit[], U> factory;

  /**
   * Create a new test instance for the given unit type {@link U}
   *
   * @param factory Factory to create new instances of the tested unit
   */
  public UnitTest(final Function<Unit[], U> factory) {
    this.factory = factory;
  }

  @Test
  public void testAccept() throws Exception {
    final Unit[] children = new Unit[5];
    Arrays.setAll(children, (i) -> mock(Unit.class));

    final U unit = this.factory.apply(children);
    final UnitVisitor visitor = mock(UnitVisitor.class);
    unit.accept(visitor);
    verifyVisit(unit, visitor);

    for (final Unit child : children) {
      verify(child).accept(eq(visitor));
    }

    verifyNoMoreInteractions(children);
    verifyNoMoreInteractions(visitor);
  }

  /**
   * Verify if the correct visit method is called on the mock, depending on the tested instance
   *
   * @param unit          The tested unit instance
   * @param mockedVisitor The mocked {@link UnitVisitor} who should have gotten a visit by the unit
   */
  abstract void verifyVisit(final U unit, final UnitVisitor mockedVisitor);

}
