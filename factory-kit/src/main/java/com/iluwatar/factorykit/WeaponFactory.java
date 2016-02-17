package com.iluwatar.factorykit;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Functional interface, an example of the factory-kit design pattern.
 * <br>Instance created locally gives an opportunity to strictly define
 * which objects types the instance of a factory will be able to create.
 * <br>Factory is a placeholder for {@link Builder}s
 * with {@link WeaponFactory#create(WeaponType)} method to initialize new objects.
 */
public interface WeaponFactory {

  /**
   * Creates an instance of the given type.
   * @param name representing enum of an object type to be created.
   * @return new instance of a requested class implementing {@link Weapon} interface.
   */
  Weapon create(WeaponType name);

  /**
   * Creates factory - placeholder for specified {@link Builder}s.
   * @param consumer for the new builder to the factory.
   * @return factory with specified {@link Builder}s
   */
  static WeaponFactory factory(Consumer<Builder> consumer) {
    HashMap<WeaponType, Supplier<Weapon>> map = new HashMap<>();
    consumer.accept(map::put);
    return name -> map.get(name).get();
  }
}
