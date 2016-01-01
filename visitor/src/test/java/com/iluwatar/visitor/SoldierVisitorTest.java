package com.iluwatar.visitor;

import java.util.Optional;

/**
 * Date: 12/30/15 - 18:59 PM
 *
 * @author Jeroen Meulemeester
 */
public class SoldierVisitorTest extends VisitorTest<SoldierVisitor> {

  /**
   * Create a new test instance for the given visitor
   */
  public SoldierVisitorTest() {
    super(
        new SoldierVisitor(),
        Optional.empty(),
        Optional.empty(),
        Optional.of("Greetings soldier")
    );
  }

}
