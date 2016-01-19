package com.iluwatar.prototype;

/**
 * 
 * ElfMage
 *
 */
public class ElfMage extends Mage {

  public ElfMage() {}

  @Override
  public Mage clone() throws CloneNotSupportedException {
    return new ElfMage();
  }

  @Override
  public String toString() {
    return "Elven mage";
  }

}
