package com.iluwatar.visitor;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Date: 12/30/15 - 19:45 PM
 *
 * @author Jeroen Meulemeester
 */
public class SergeantTest extends UnitTest<Sergeant> {

  /**
   * Create a new test instance for the given {@link Sergeant}
   */
  public SergeantTest() {
    super(Sergeant::new);
  }

  @Override
  void verifyVisit(Sergeant unit, UnitVisitor mockedVisitor) {
    verify(mockedVisitor).visitSergeant(eq(unit));
  }

}