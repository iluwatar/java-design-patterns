package com.iluwatar.privateclassdata;

/**
 * 
 * Stew ingredients
 *
 */
public class StewData {

  private int numPotatoes;
  private int numCarrots;
  private int numMeat;
  private int numPeppers;

  /**
   * Constructor
   */
  public StewData(int numPotatoes, int numCarrots, int numMeat, int numPeppers) {
    this.numPotatoes = numPotatoes;
    this.numCarrots = numCarrots;
    this.numMeat = numMeat;
    this.numPeppers = numPeppers;
  }

  public int getNumPotatoes() {
    return numPotatoes;
  }

  public int getNumCarrots() {
    return numCarrots;
  }

  public int getNumMeat() {
    return numMeat;
  }

  public int getNumPeppers() {
    return numPeppers;
  }
}
