package com.iluwatar.acyclicvisitor;

/**
 * HayesVisitor interface for Hayes-specific logic.
 */
public interface HayesVisitor extends SpecificModemVisitor {
  void visit(Hayes hayes); // Supports Hayes-specific behavior
}
