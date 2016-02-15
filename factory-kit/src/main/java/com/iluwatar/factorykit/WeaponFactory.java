package com.iluwatar.factorykit;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Functional interface that represents factory kit. Instance created locally gives an opportunity to strictly define
 * which objects types the instance of a factory would be able to create. Factory is just a placeholder for builders with
 * create method to initialize new objects.
 */
public interface WeaponFactory {

  Weapon create(WeaponType name);

  static WeaponFactory factory(Consumer<Builder> consumer) {
    HashMap<WeaponType, Supplier<Weapon>> map = new HashMap<>();
    consumer.accept(map::put);
    return name -> map.get(name).get();
  }
}
