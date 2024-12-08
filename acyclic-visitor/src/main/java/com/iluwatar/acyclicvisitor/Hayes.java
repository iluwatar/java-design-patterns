package com.iluwatar.acyclicvisitor;

import lombok.extern.slf4j.Slf4j;

/**
 * Hayes class implements its accept method.
 */
@Slf4j
public class Hayes implements Modem {

  /**
   * Accepts all visitors and interacts with SpecificModemVisitor.
   */
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof SpecificModemVisitor) {
      ((SpecificModemVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Unsupported visitor type for Hayes modem");
    }
  }

  /**
   * Hayes modem's toString method.
   */
  @Override
  public String toString() {
    return "Hayes modem";
  }
}
