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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PersonFinderTest {
  @Test
  void personFoundInDB(){
    // personFinderInstance
    PersonFinder personFinder = new PersonFinder();
    // init database for our personFinder
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    // Dummy persons
    Person person1 = new Person(1, "John", 27304159);
    Person person2 = new Person(2, "Thomas", 42273631);
    Person person3 = new Person(3, "Arthur", 27489171);
    Person person4 = new Person(4, "Finn", 20499078);
    Person person5 = new Person(5, "Michael", 40599078);
    // Add data to the database.
    db.insert(person1);
    db.insert(person2);
    db.insert(person3);
    db.insert(person4);
    db.insert(person5);
    personFinder.setDb(db);

    Assertions.assertEquals(person1,personFinder.getPerson(1),"Find person returns incorrect record.");
    Assertions.assertEquals(person3,personFinder.getPerson(3),"Find person returns incorrect record.");
    Assertions.assertEquals(person2,personFinder.getPerson(2),"Find person returns incorrect record.");
    Assertions.assertEquals(person5,personFinder.getPerson(5),"Find person returns incorrect record.");
    Assertions.assertEquals(person4,personFinder.getPerson(4),"Find person returns incorrect record.");
  }
  @Test
  void personFoundInIdMap(){
    // personFinderInstance
    PersonFinder personFinder = new PersonFinder();
    // init database for our personFinder
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    // Dummy persons
    Person person1 = new Person(1, "John", 27304159);
    Person person2 = new Person(2, "Thomas", 42273631);
    Person person3 = new Person(3, "Arthur", 27489171);
    Person person4 = new Person(4, "Finn", 20499078);
    Person person5 = new Person(5, "Michael", 40599078);
    // Add data to the database.
    db.insert(person1);
    db.insert(person2);
    db.insert(person3);
    db.insert(person4);
    db.insert(person5);
    personFinder.setDb(db);
    // Assure key is not in the ID map.
    Assertions.assertFalse(personFinder.getIdentityMap().getPersonMap().containsKey(3));
    // Assure key is in the database.
    Assertions.assertEquals(person3,personFinder.getPerson(3),"Finder returns incorrect record.");
    // Assure that the record for this key is cached in the Map now.
    Assertions.assertTrue(personFinder.getIdentityMap().getPersonMap().containsKey(3));
    // Find the record again. This time it will be found in the map.
    Assertions.assertEquals(person3,personFinder.getPerson(3),"Finder returns incorrect record.");
  }
  @Test
  void personNotFoundInDB(){
    PersonFinder personFinder = new PersonFinder();
    // init database for our personFinder
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    personFinder.setDb(db);
    Assertions.assertThrows(IdNotFoundException.class,()->personFinder.getPerson(1));
    // Dummy persons
    Person person1 = new Person(1, "John", 27304159);
    Person person2 = new Person(2, "Thomas", 42273631);
    Person person3 = new Person(3, "Arthur", 27489171);
    Person person4 = new Person(4, "Finn", 20499078);
    Person person5 = new Person(5, "Michael", 40599078);
    db.insert(person1);
    db.insert(person2);
    db.insert(person3);
    db.insert(person4);
    db.insert(person5);
    personFinder.setDb(db);
    // Assure that the database has been updated.
    Assertions.assertEquals(person4,personFinder.getPerson(4),"Find returns incorrect record");
    // Assure key is in DB now.
    Assertions.assertDoesNotThrow(()->personFinder.getPerson(1));
    // Assure key not in DB.
    Assertions.assertThrows(IdNotFoundException.class,()->personFinder.getPerson(6));

  }
}
