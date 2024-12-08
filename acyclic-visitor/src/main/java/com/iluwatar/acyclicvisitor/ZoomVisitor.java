package com.iluwatar.acyclicvisitor;

/**
 * ZoomVisitor interface, extending SpecificModemVisitor for Zoom-specific logic.
 */
public interface ZoomVisitor extends SpecificModemVisitor {
  void visit(Zoom zoom); // Still supports Zoom-specific operations
}
