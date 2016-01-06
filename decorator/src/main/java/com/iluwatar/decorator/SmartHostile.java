package com.iluwatar.decorator;

/**
 * SmartHostile is a decorator for {@link Hostile} objects. The calls to the {@link Hostile} interface
 * are intercepted and decorated. Finally the calls are delegated to the decorated {@link Hostile}
 * object.
 *
 */
public class SmartHostile implements Hostile {

  private Hostile decorated;

  public SmartHostile(Hostile decorated) {
    this.decorated = decorated;
  }

  @Override
  public void attack() {
    System.out.println("It throws a rock at you!");
    decorated.attack();
  }

  @Override
  public int getAttackPower() {
    // decorated hostile's power + 20 because it is smart
    return decorated.getAttackPower() + 20;
  }

  @Override
  public void fleeBattle() {
    System.out.println("It calls for help!");
    decorated.fleeBattle();
  }
}
