package com.iluwatar.visitor;

import java.util.Optional;

/**
 * Date: 12/30/15 - 18:36 PM
 *
 * @author Jeroen Meulemeester
 */
public class SergeantVisitorTest extends VisitorTest<SergeantVisitor> {

  /**
   * Create a new test instance for the given visitor
   */
  public SergeantVisitorTest() {
    super(
        new SergeantVisitor(),
        Optional.empty(),
        Optional.of("Hello sergeant"),
        Optional.empty()
    );
  }

}
