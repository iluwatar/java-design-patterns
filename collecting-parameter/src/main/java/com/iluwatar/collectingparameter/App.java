package com.iluwatar.collectingparameter;

import java.util.ArrayList;
import java.util.List;

/**
 * The intention of the collecting parameter pattern is to help reduce the bulky method which needs
 * to collect information among different objects.This pattern throw an object as parameter to the
 * objects need to collect information from, and add those information to the parameter object.
 * In this example, we want to collect information from the {@link Apple} and throw the weightInfo
 * and typeInfo to the {@link Apple} object to collect their weight and type.
 */
public class App {
  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    List<Apple> apples = new ArrayList<>();

    apples.add(new Apple(10, "a"));
    apples.add(new Apple(20, "b"));
    apples.add(new Apple(15, "c"));

    collectType(apples);
    collectWeight(apples);
  }

  /**
   * collect the Apple weight information by throwing weightInfo to apples.
   *
   * @param apples the apple list need to collect information from
   */
  public static void collectWeight(List<Apple> apples) {
    List<String> weightInfo = new ArrayList<>();
    for (Apple apple : apples) {
      apple.writeWeightTo(weightInfo);
    }
    for (String message : weightInfo) {
      System.out.print(message);
    }
  }

  /**
   * collect the Apple type information by throwing typeInfo to apples.
   *
   * @param apples the apple list need to collect information from
   */
  public static void collectType(List<Apple> apples) {
    List<String> typeInfo = new ArrayList<>();
    for (Apple apple : apples) {
      apple.writeTypeTo(typeInfo);
    }
    for (String message : typeInfo) {
      System.out.print(message);
    }
  }
}
