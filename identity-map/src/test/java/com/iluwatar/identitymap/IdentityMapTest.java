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

class IdentityMapTest {
  @Test
  void addToMap(){
    // new instance of an identity map(not connected to any DB here)
    IdentityMap idMap = new IdentityMap();
    // Dummy person instances
    Person person1 = new Person(11, "Michael", 27304159);
    Person person2 = new Person(22, "John", 42273631);
    Person person3 = new Person(33, "Arthur", 27489171);
    Person person4 = new Person(44, "Finn", 20499078);
    // id already in map
    Person person5 = new Person(11, "Michael", 40599078);
    // All records go into identity map
    idMap.addPerson(person1);
    idMap.addPerson(person2);
    idMap.addPerson(person3);
    idMap.addPerson(person4);
    idMap.addPerson(person5);
    // Test no duplicate in our Map.
    Assertions.assertEquals(4,idMap.size(),"Size of the map is incorrect");
    // Test record not updated by add method.
    Assertions.assertEquals(27304159,idMap.getPerson(11).getPhoneNum(),"Incorrect return value for phone number");
  }
  @Test
  void testGetFromMap() {
    // new instance of an identity map(not connected to any DB here)
    IdentityMap idMap = new IdentityMap();
    // Dummy person instances
    Person person1 = new Person(11, "Michael", 27304159);
    Person person2 = new Person(22, "John", 42273631);
    Person person3 = new Person(33, "Arthur", 27489171);
    Person person4 = new Person(44, "Finn", 20499078);
    Person person5 = new Person(55, "Michael", 40599078);
    // All records go into identity map
    idMap.addPerson(person1);
    idMap.addPerson(person2);
    idMap.addPerson(person3);
    idMap.addPerson(person4);
    idMap.addPerson(person5);
    // Test for dummy persons in the map
    Assertions.assertEquals(person1,idMap.getPerson(11),"Incorrect person record returned");
    Assertions.assertEquals(person4,idMap.getPerson(44),"Incorrect person record returned");
    // Test for person with given id not in map
    Assertions.assertNull(idMap.getPerson(1), "Incorrect person record returned");
  }
}
