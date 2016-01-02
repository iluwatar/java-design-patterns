package com.iluwatar.property;

/**
 * Interface for prototype inheritance
 */
public interface Prototype {

  Integer get(Stats stat);

  boolean has(Stats stat);

  void set(Stats stat, Integer val);

  void remove(Stats stat);
}
