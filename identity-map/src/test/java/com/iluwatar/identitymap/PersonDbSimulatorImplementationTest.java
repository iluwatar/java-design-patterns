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

class PersonDbSimulatorImplementationTest {
  @Test
  void testInsert(){
    // DataBase initialization.
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    Assertions.assertEquals(0,db.size(),"Size of null database should be 0");
    // Dummy persons.
    Person person1 = new Person(1, "Thomas", 27304159);
    Person person2 = new Person(2, "John", 42273631);
    Person person3 = new Person(3, "Arthur", 27489171);
    db.insert(person1);
    db.insert(person2);
    db.insert(person3);
    // Test size after insertion.
    Assertions.assertEquals(3,db.size(),"Incorrect size for database.");
    Person person4 = new Person(4, "Finn", 20499078);
    Person person5 = new Person(5, "Michael", 40599078);
    db.insert(person4);
    db.insert(person5);
    // Test size after more insertions.
    Assertions.assertEquals(5,db.size(),"Incorrect size for database.");
    Person person5duplicate = new Person(5,"Kevin",89589122);
    db.insert(person5duplicate);
    // Test size after attempt to insert record with duplicate key.
    Assertions.assertEquals(5,db.size(),"Incorrect size for data base");
  }
  @Test
  void findNotInDb(){
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    Person person1 = new Person(1, "Thomas", 27304159);
    Person person2 = new Person(2, "John", 42273631);
    db.insert(person1);
    db.insert(person2);
    // Test if IdNotFoundException is thrown where expected.
    Assertions.assertThrows(IdNotFoundException.class,()->db.find(3));
  }
  @Test
  void findInDb(){
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    Person person1 = new Person(1, "Thomas", 27304159);
    Person person2 = new Person(2, "John", 42273631);
    db.insert(person1);
    db.insert(person2);
    Assertions.assertEquals(person2,db.find(2),"Record that was found was incorrect.");
  }
  @Test
  void updateNotInDb(){
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    Person person1 = new Person(1, "Thomas", 27304159);
    Person person2 = new Person(2, "John", 42273631);
    db.insert(person1);
    db.insert(person2);
    Person person3 = new Person(3,"Micheal",25671234);
    // Test if IdNotFoundException is thrown when person with ID 3 is not in DB.
    Assertions.assertThrows(IdNotFoundException.class,()->db.update(person3));
  }
  @Test
  void updateInDb(){
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    Person person1 = new Person(1, "Thomas", 27304159);
    Person person2 = new Person(2, "John", 42273631);
    db.insert(person1);
    db.insert(person2);
    Person person = new Person(2,"Thomas",42273690);
    db.update(person);
    Assertions.assertEquals(person,db.find(2),"Incorrect update.");
  }
  @Test
  void deleteNotInDb(){
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    Person person1 = new Person(1, "Thomas", 27304159);
    Person person2 = new Person(2, "John", 42273631);
    db.insert(person1);
    db.insert(person2);
    // Test if IdNotFoundException is thrown when person with this ID not in DB.
    Assertions.assertThrows(IdNotFoundException.class,()->db.delete(3));
  }
  @Test
  void deleteInDb(){
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    Person person1 = new Person(1, "Thomas", 27304159);
    Person person2 = new Person(2, "John", 42273631);
    db.insert(person1);
    db.insert(person2);
    // delete the record.
    db.delete(1);
    // test size of database after deletion.
    Assertions.assertEquals(1,db.size(),"Size after deletion is incorrect.");
    // try to find deleted record in db.
    Assertions.assertThrows(IdNotFoundException.class,()->db.find(1));
  }

}
