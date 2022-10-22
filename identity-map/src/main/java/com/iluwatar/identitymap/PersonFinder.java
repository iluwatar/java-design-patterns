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

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Any object of this class stores a DataBase and an Identity Map. When we try to look for a key we first check if
 * it has been cached in the Identity Map and return it if it is indeed in the map.
 * If that is not the case then go to the DataBase, get the record, store it in the
 * Identity Map and then return the record. Now if we look for the record again we will find it in the table itself which
 * will make lookup faster.
 */
@Slf4j
@Getter
@Setter
public class PersonFinder {
  private static final long serialVersionUID = 1L;
  //  Access to the Identity Map
  private IdentityMap identityMap = new IdentityMap();
  private PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
  /**
   * get person corresponding to input ID.
   *
   * @param key : personNationalId to look for.
   */
  public Person getPerson(int key) {
    // Try to find person in the identity map
    Person person = this.identityMap.getPerson(key);
    if (person != null) {
      LOGGER.info("Person found in the Map");
      return person;
    } else {
      // Try to find person in the database
      person = this.db.find(key);
      if (person != null) {
        this.identityMap.addPerson(person);
        LOGGER.info("Person found in DB.");
        return person;
      }
      LOGGER.info("Person with this ID does not exist.");
      return null;
    }
  }
}
