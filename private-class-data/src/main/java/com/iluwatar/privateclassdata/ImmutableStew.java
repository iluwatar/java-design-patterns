package com.iluwatar.privateclassdata;

/**
 * 
 * Immutable stew class, protected with Private Class Data pattern
 *
 */
public class ImmutableStew {

  private StewData data;

  public ImmutableStew(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {
    data = new StewData(numPotatoes, numCarrots, numMeat, numPeppers);
  }

  /**
   * Mix the stew
   */
  public void mix() {
    System.out.println(String.format(
        "Mixing the immutable stew we find: %d potatoes, %d carrots, %d meat and %d peppers",
        data.getNumPotatoes(), data.getNumCarrots(), data.getNumMeat(), data.getNumPeppers()));
  }
}
