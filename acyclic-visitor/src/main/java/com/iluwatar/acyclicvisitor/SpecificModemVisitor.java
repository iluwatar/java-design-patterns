package com.iluwatar.acyclicvisitor;

/**
 * General visitor interface for specific modems.
 */
public interface SpecificModemVisitor extends ModemVisitor {
  void visit(Modem modem);
}
