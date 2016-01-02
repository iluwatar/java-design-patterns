package com.iluwatar.visitor;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/30/15 - 18:59 PM
 *
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