package com.iluwatar.prototype;

/**
 * 
 * OrcBeast
 *
 */
public class OrcBeast extends Beast {

  public OrcBeast() {}

  @Override
  public Beast clone() throws CloneNotSupportedException {
    return new OrcBeast();
  }

  @Override
  public String toString() {
    return "Orcish wolf";
  }

}
