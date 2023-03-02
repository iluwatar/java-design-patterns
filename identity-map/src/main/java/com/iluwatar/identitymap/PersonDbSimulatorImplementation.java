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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * This is a sample database implementation. The database is in the form of an arraylist which stores records of
 * different persons. The personNationalId acts as the primary key for a record.
 * Operations :
 * -> find (look for object with a particular ID)
 * -> insert (insert record for a new person into the database)
 * -> update (update the record of a person). To do this, create a new person instance with the same ID as the record you
 * want to update. Then call this method with that person as an argument.
 * -> delete (delete the record for a particular ID)
 */
@Slf4j
public class PersonDbSimulatorImplementation implements PersonDbSimulator {

  //This simulates a table in the database. To extend logic to multiple tables just add more lists to the implementation.
  private List<Person> personList = new ArrayList<>();
  static final String NOT_IN_DATA_BASE = " not in DataBase";
  static final String ID_STR = "ID : ";

  @Override
  public Person find(int personNationalId) throws IdNotFoundException {
    Optional<Person> elem = personList.stream().filter(p -> p.getPersonNationalId() == personNationalId).findFirst();
    if (elem.isEmpty()) {
      throw new IdNotFoundException(ID_STR + personNationalId + NOT_IN_DATA_BASE);
    }
    LOGGER.info(elem.get().toString());
    return elem.get();
  }

  @Override
  public void insert(Person person) {
    Optional<Person> elem = personList.stream().filter(p -> p.getPersonNationalId() == person.getPersonNationalId()).findFirst();
    if (elem.isPresent()) {
      LOGGER.info("Record already exists.");
      return;
    }
    personList.add(person);
  }

  @Override
  public void update(Person person) throws IdNotFoundException {
    Optional<Person> elem = personList.stream().filter(p -> p.getPersonNationalId() == person.getPersonNationalId()).findFirst();
    if (elem.isPresent()) {
      elem.get().setName(person.getName());
      elem.get().setPhoneNum(person.getPhoneNum());
      LOGGER.info("Record updated successfully");
      return;
    }
    throw new IdNotFoundException(ID_STR + person.getPersonNationalId() + NOT_IN_DATA_BASE);
  }

  /**
   * Delete the record corresponding to given ID from the DB.
   *
   * @param id : personNationalId for person whose record is to be deleted.
   */
  public void delete(int id) throws IdNotFoundException {
    Optional<Person> elem = personList.stream().filter(p -> p.getPersonNationalId() == id).findFirst();
    if (elem.isPresent()) {
      personList.remove(elem.get());
      LOGGER.info("Record deleted successfully.");
      return;
    }
    throw new IdNotFoundException(ID_STR + id + NOT_IN_DATA_BASE);
  }

  /**
   * Return the size of the database.
   */
  public int size() {
    if (personList == null) {
      return 0;
    }
    return personList.size();
  }

}
