package com.iluwatar.value.object;

/**
 * Hello world!.
 *
 */
public class App {
  public static void main(String[] args) {
    HeroStat stat = HeroStat.valueOf(10, 5, 0);
    System.out.println(stat.toString());
  }
}
