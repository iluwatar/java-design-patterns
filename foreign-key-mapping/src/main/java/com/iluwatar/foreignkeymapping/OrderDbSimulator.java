package com.iluwatar.foreignkeymapping;

public interface OrderDbSimulator {
  Order find(int orderNationalId);

  void insert(Order order);

  void update(Order order);

  void delete(int orderNationalId);
}