package com.iluwatar.visitor;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Date: 12/30/15 - 19:45 PM
 *
 * @author Jeroen Meulemeester
 */
public class SoldierTest extends UnitTest<Soldier> {

  /**
   * Create a new test instance for the given {@link Soldier}
   */
  public SoldierTest() {
    super(Soldier::new);
  }

  @Override
  void verifyVisit(Soldier unit, UnitVisitor mockedVisitor) {
    verify(mockedVisitor).visitSoldier(eq(unit));
  }

}