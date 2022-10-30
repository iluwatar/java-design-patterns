package com.iluwatar.activeobject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Useful examples of common useage of the program.
 */
public class App {

  /**
   * Use this method to become familiar with the program.
   *
   * @param args arguments passed into the main method.
   */
  public static void main(String[] args) {
    try {
      ActiveDatabase activeDatabase = new ActiveDatabase("world", "root", "apple-trunks", "localhost:3306", "city");
      ActiveRow ar = new ActiveRow(activeDatabase, "101");
      ar.contents = new ArrayList<String>(
          Arrays.asList("101", "Godoy Cruz", "ARG", "Mendoza", "206998"));
      ar.write();
      System.out.println(ar.read());

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
