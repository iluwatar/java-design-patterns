package com.iluwatar.foreignkeymapping;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a sample database implementation. The database is in the form of an arraylist which stores records of
 * different persons. The personNationalId acts as the primary key for a record.
 * Operations :
 * -> find (look for object with a particular ID)
 * -> insert (insert record for a new person into the database)
 * -> update (update the record of a person). To do this, create a new person instance with the same ID as the record you
 *    want to update. Then call this method with that person as an argument.
 * -> delete (delete the record for a particular ID)
 */
@Slf4j
public class PersonDbSimulatorImplementation implements PersonDbSimulator {

  //    This simulates a database.
  private List<Person> personList = new ArrayList<>();

  @Override
  public Person find(int personNationalID) throws IdNotFoundException {
    for (Person elem : personList) {
      if (elem.getPersonNationalId() == personNationalID) {
        return elem;
      }
    }
    throw new IdNotFoundException("ID : " + personNationalID + " not in DataBase");
  }

  @Override
  public void insert(Person person) {
    for (Person elem : personList) {
      if (elem.getPersonNationalId() == person.getPersonNationalId()) {
        LOGGER.info("Record already exists.");
        return;
      }
    }
    personList.add(person);
  }

  @Override
  public void update(Person person) throws IdNotFoundException {
    for (Person elem : personList) {
      if (elem.getPersonNationalId() == person.getPersonNationalId()) {
        elem.setFirstName(person.getFirstName());
        elem.setAge(person.getAge());
        LOGGER.info("Record updated successfully");
        return;
      }
    }
    throw new IdNotFoundException("ID : " + person.getPersonNationalId() + " not in DataBase");
  }

  /**
   * Delete the record corresponding to given ID from the DB.
   *
   * @param id : personNationalId for person whose record is to be deleted.
   */
  public void delete(int id) throws IdNotFoundException {
    for (Person elem : personList) {
      if (elem.getPersonNationalId() == id) {
        personList.remove(elem);
        LOGGER.info("Record deleted successfully.");
        return;
      }
    }
    throw new IdNotFoundException("ID : " + id + " not in DataBase");
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