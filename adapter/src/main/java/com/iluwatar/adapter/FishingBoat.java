package com.iluwatar.adapter;

/**
 *
 * Device class (adaptee in the pattern). We want to reuse this class
 *
 */
public class FishingBoat {

  public void sail() {
    System.out.println("The Boat is moving to that place");
  }

  public void fish() {
    System.out.println("fishing ...");
  }

}
