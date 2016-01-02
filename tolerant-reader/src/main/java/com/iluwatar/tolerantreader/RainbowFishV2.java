package com.iluwatar.tolerantreader;

/**
 * 
 * RainbowFishV2 is the evolved schema
 *
 */
public class RainbowFishV2 extends RainbowFish {

  private static final long serialVersionUID = 1L;

  private boolean sleeping;
  private boolean hungry;
  private boolean angry;

  public RainbowFishV2(String name, int age, int lengthMeters, int weightTons) {
    super(name, age, lengthMeters, weightTons);
  }

  /**
   * Constructor
   */
  public RainbowFishV2(String name, int age, int lengthMeters, int weightTons, boolean sleeping,
      boolean hungry, boolean angry) {
    this(name, age, lengthMeters, weightTons);
    this.sleeping = sleeping;
    this.hungry = hungry;
    this.angry = angry;
  }

  public boolean getSleeping() {
    return sleeping;
  }

  public boolean getHungry() {
    return hungry;
  }

  public boolean getAngry() {
    return angry;
  }
}
