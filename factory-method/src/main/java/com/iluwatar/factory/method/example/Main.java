package com.iluwatar.factory.method.example;

public class Main {
  public static void main(String[] args) {
    Car sports = CarFactory.createCar("sports");
    sports.drive();

    Car suv = CarFactory.createCar("suv");
    suv.drive();
  }
}
