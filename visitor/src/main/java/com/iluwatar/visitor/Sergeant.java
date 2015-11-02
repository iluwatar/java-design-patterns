package com.iluwatar.visitor;

/**
 * 
 * Sergeant
 *
 */
public class Sergeant extends Unit {

  public Sergeant(Unit... children) {
    super(children);
  }

  @Override
  public void accept(UnitVisitor visitor) {
    visitor.visitSergeant(this);
    super.accept(visitor);
  }

  @Override
  public String toString() {
    return "sergeant";
  }
}
