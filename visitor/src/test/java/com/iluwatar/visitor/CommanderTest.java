package com.iluwatar.visitor;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Date: 12/30/15 - 19:45 PM
 *
 * @author Jeroen Meulemeester
 */
public class CommanderTest extends UnitTest<Commander> {

  /**
   * Create a new test instance for the given {@link Commander}
   */
  public CommanderTest() {
    super(Commander::new);
  }

  @Override
  void verifyVisit(Commander unit, UnitVisitor mockedVisitor) {
    verify(mockedVisitor).visitCommander(eq(unit));
  }

}