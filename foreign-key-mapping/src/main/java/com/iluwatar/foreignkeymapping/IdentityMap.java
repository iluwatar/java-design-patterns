package com.iluwatar.foreignkeymapping;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * This class stores the map into which we will be caching records after loading them from a DataBase.
 * Stores the records as a Hash Map with the personNationalIDs as keys.
 */
@Slf4j
public class IdentityMap {
  private HashMap<Integer, Person> personMap = new HashMap<>();
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
    }
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

  public HashMap<Integer, Person> getPersonMap() {
    return this.personMap;
  }
}