package com.iluwatar.foreignkeymapping;

public interface AppDbSimulator {
  Object find(int id, String table);

  void insert(Object object, String table);

  void update(Object object, String table);

  void delete(int id, String table);
}
