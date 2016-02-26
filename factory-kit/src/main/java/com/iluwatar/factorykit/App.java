package com.iluwatar.factorykit;

/**
 * Factory-kit is a creational pattern which defines a factory of immutable content
 * with separated builder and factory interfaces to deal with the problem of
 * creating one of the objects specified directly in the factory-kit instance.
 *
 * <p>
 * In the given example {@link WeaponFactory} represents the factory-kit, that contains
 * four {@link Builder}s for creating new objects of
 * the classes implementing {@link Weapon} interface.
 * <br>Each of them can be called with {@link WeaponFactory#create(WeaponType)} method, with
 * an input representing an instance of {@link WeaponType} that needs to
 * be mapped explicitly with desired class type in the factory instance.
 */
public class App {
  /**
   * Program entry point.
   *
   * @param args @param args command line args
   */
  public static void main(String[] args) {
    WeaponFactory factory = WeaponFactory.factory(builder -> {
      builder.add(WeaponType.SWORD, Sword::new);
      builder.add(WeaponType.AXE, Axe::new);
      builder.add(WeaponType.SPEAR, Spear::new);
      builder.add(WeaponType.BOW, Bow::new);
    });
    Weapon axe = factory.create(WeaponType.AXE);
    System.out.println(axe);
  }
}
