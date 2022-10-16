package com.iluwatar.foreignkeymapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Any object of this class stores a DataBase and an Identity Map. When we try to look for a key we first check if
 * it has been cached in the Identity Map and return it if it is indeed in the map.
 * If that is not the case then go to the DataBase, get the record, store it in the
 * Identity Map and then return the record. Now if we look for the record again we will find it in the table itself which
 * will make lookup faster.
 */
@Slf4j
public class PersonFinder {
  //  Access to the Identity Map
  private IdentityMap identityMap = new IdentityMap();
  //  Access to the DataBase
  private PersonDbSimulatorImplementation db;
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

  public PersonDbSimulatorImplementation getDB() {
    return db;
  }

  public IdentityMap getIdentityMap() {
    return identityMap;
  }

  public void setDB(PersonDbSimulatorImplementation db) {
    this.db = db;
  }

  public void setIdentityMap(IdentityMap identityMap) {
    this.identityMap = identityMap;
  }
}