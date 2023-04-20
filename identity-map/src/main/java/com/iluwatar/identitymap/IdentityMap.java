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

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class stores the map into which we will be caching records after loading them from a DataBase.
 * Stores the records as a Hash Map with the personNationalIDs as keys.
 */
@Slf4j
@Getter
public class IdentityMap {
  private Map<Integer, Person> personMap = new HashMap<>();
  /**
   * Add person to the map.
   */
  public void addPerson(Person person) {
    if (!personMap.containsKey(person.getPersonNationalId())) {
      personMap.put(person.getPersonNationalId(), person);
    } else { // Ensure that addPerson does not update a record. This situation will never arise in our implementation. Added only for testing purposes.
      LOGGER.info("Key already in Map");
    }
  }

  /**
   * Get Person with given id.
   *
   * @param id : personNationalId as requested by user.
   */
  public Person getPerson(int id) {
    Person person = personMap.get(id);
    if (person == null) {
      LOGGER.info("ID not in Map.");
      return null;
    }
    LOGGER.info(person.toString());
    return person;
  }

  /**
   * Get the size of the map.
   */
  public int size() {
    if (personMap == null) {
      return 0;
    }
    return personMap.size();
  }

}
