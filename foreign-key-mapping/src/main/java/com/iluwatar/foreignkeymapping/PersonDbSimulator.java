package com.iluwatar.foreignkeymapping;

public interface PersonDbSimulator {
  Person find(int personNationalID);

  void insert(Person person);

  void update(Person person);

  void delete(int personNationalID);
}