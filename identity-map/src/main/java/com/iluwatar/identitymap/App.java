/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.identitymap;

import lombok.extern.slf4j.Slf4j;

/**
 * The basic idea behind the Identity Map is to have a series of maps containing objects that have been pulled from the database.
 * The below example demonstrates the identity map pattern by creating a sample DB.
 * Since only 1 DB has been created we only have 1 map corresponding to it for the purpose of this demo.
 * When you load an object from the database, you first check the map.
 * If there’s an object in it that corresponds to the one you’re loading, you return it. If not, you go to the database,
 * putting the objects on the map for future reference as you load them.
 */
@Slf4j
public class App {
  /**
   * Program entry point.
   *
   * @param args command line args.
   */
  public static void main(String[] args) {

    // Dummy Persons
    Person person1 = new Person(1, "John", 27304159);
    Person person2 = new Person(2, "Thomas", 42273631);
    Person person3 = new Person(3, "Arthur", 27489171);
    Person person4 = new Person(4, "Finn", 20499078);
    Person person5 = new Person(5, "Michael", 40599078);

    // Init database
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    db.insert(person1);
    db.insert(person2);
    db.insert(person3);
    db.insert(person4);
    db.insert(person5);

    // Init a personFinder
    PersonFinder finder = new PersonFinder();
    finder.setDb(db);

    // Find persons in DataBase not the map.
    LOGGER.info(finder.getPerson(2).toString());
    LOGGER.info(finder.getPerson(4).toString());
    LOGGER.info(finder.getPerson(5).toString());
    // Find the person in the map.
    LOGGER.info(finder.getPerson(2).toString());
  }
}
