package com.iluwatar.acyclicvisitor;

import lombok.extern.slf4j.Slf4j;

/**
 * Zoom class implements its accept method.
 */
@Slf4j
public class Zoom implements Modem {

  /**
   * Accepts all visitors but interacts generically with SpecificModemVisitor.
   */
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof SpecificModemVisitor) {
      ((SpecificModemVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Unsupported visitor type for Zoom modem");
    }
  }

  /**
   * Zoom modem's toString method.
   */
  @Override
  public String toString() {
    return "Zoom modem";
  }
}
