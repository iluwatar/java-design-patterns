package com.iluwatar.tabledatagateway;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Person gate way.
 */
@Slf4j
public class PersonGateWay {
  /**
   * The Person table.
   */
  private final ArrayList<Person> personTable = new ArrayList<>();

  /**
   * Find person.
   *
   * @param id the id
   * @return the person
   */
  public Person find(int id) {
    if (id >= personTable.size() || id < 0) {
      return null;
    }
    return personTable.get(id);
  }

  /**
   * Find by first name array list.
   *
   * @param firstName the first name
   * @return the array list
   */
  public List<Person> findByFirstName(String firstName) {
    List<Person> result = new ArrayList<>();
    personTable.forEach(
        x -> {
          if (x.getFirstName().equals(firstName)) {
            result.add(x);
          }
        });
    return result;
  }

  /**
   * Update.
   *
   * @param id        the id
   * @param firstName the first name
   * @param lastName  the last name
   * @param gender    the gender
   * @param age       the age
   */
  public void update(int id, String firstName, String lastName, String gender, int age) throws Exception {
    if (id >= personTable.size() || id < 0) {
      throw new Exception("The input ID is wrong!");
    }
    if (age < 0) {
      throw new Exception("The input age is wrong!");
    }
    personTable.get(id).setFirstName(firstName);
    personTable.get(id).setLastName(lastName);
    personTable.get(id).setGender(gender);
    personTable.get(id).setAge(age);
    LOGGER.info("Update successfully");
  }

  /**
   * Insert.
   *
   * @param firstName the first name
   * @param lastName  the last name
   * @param gender    the gender
   * @param age       the age
   */
  public void insert(String firstName, String lastName, String gender, int age) throws Exception {
    if (age < 0) {
      throw new Exception("The input age is wrong!");
    }
    int id = personTable.size();
    personTable.add(new Person(id, firstName, lastName, gender, age));
    LOGGER.info("Insert successfully");
  }

  /**
   * Delete boolean.
   *
   * @param id the id
   * @return the boolean
   */
  public boolean delete(int id) {
    if (id >= personTable.size() || id < 0) {
      return false;
    }

    personTable.remove(id);
    for (int i = id; i < personTable.size(); i++) {
      personTable.get(i).setId(i);
    }
    LOGGER.info("delete successfully");
    return true;
  }
}