package com.iluwatar.visitor;

import java.util.Optional;

/**
 * Date: 12/30/15 - 18:43 PM
 *
 * @author Jeroen Meulemeester
 */
public class CommanderVisitorTest extends VisitorTest<CommanderVisitor> {

  /**
   * Create a new test instance for the given visitor
   */
  public CommanderVisitorTest() {
    super(
        new CommanderVisitor(),
        Optional.of("Good to see you commander"),
        Optional.empty(),
        Optional.empty()
    );
  }

}
