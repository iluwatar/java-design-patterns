package com.iluwatar.tabledatagateway;

import java.util.ArrayList;

/**
 * The type App.
 */
public class App {
  /**
   * Entry point of the main program.
   *
   * @param args Program runtime arguments.
   */
  public static void main(String[] args) {
    PersonGateWay personGateWay = new PersonGateWay();
    personGateWay.insert("Tony", "Stark", "M", 36);
    personGateWay.insert("Thor", "Odinson", "M", 40);
    personGateWay.insert("Natasha", "Romanoff", "F", 28);
    ArrayList<Person> firstNameSet = personGateWay.findByFirstName("Tony");
    Person personFindById = personGateWay.find(1);
    personGateWay.update(1, "Thor", "Odinson", "M", 41);
    personGateWay.delete(0);
  }
}
