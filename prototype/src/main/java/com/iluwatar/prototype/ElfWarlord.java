package com.iluwatar.prototype;

/**
 * 
 * ElfWarlord
 *
 */
public class ElfWarlord extends Warlord {

  public ElfWarlord() {}

  @Override
  public Warlord clone() throws CloneNotSupportedException {
    return new ElfWarlord();
  }

  @Override
  public String toString() {
    return "Elven warlord";
  }

}
