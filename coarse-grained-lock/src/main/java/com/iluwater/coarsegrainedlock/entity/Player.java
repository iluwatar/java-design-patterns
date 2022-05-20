package com.iluwater.coarsegrainedlock.entity;

import lombok.extern.slf4j.Slf4j;

/**
 * Player class
 * Store the information of a basketball player.
 */
@Slf4j
public class Player {
  private final String lastName;
  private final String firstName;
  private int champions;
  private final Address address;
  private final Object mutex;

  /**
   * Construct function.
   *
   * @param lastName  Lastname of the player
   * @param firstName Firstname of the player
   * @param city      The city where the player lives
   * @param state     The state where the city locates
   */
  public Player(String lastName, String firstName, String city, String state) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.champions = 0;
    this.address = new Address(this, city, state);
    this.mutex = new Object();
  }


  /**
   * Get the mutex lock of the player.
   *
   * @return Mutex lock
   */
  public Object getMutex() {
    return mutex;
  }

  /**
   * Get the firstname of the player.
   *
   * @return The firstname of the player
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Get the number of champion of the player.
   *
   * @return The number of champion of the player.
   */
  public int getChampions() {
    return champions;
  }

  /**
   * Get the city where the player lives.
   *
   * @return city
   */
  public String getCity() {
    return address.getCity();
  }

  /**
   * Get the state where the player lives.
   *
   * @return state
   */
  public String getState() {
    return address.getState();
  }

  /**
   * Get the lastname of the player.
   *
   * @return The lastname of the player
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Add the champion by 1.
   */
  public void winChampion() {
    synchronized (mutex) {
      LOGGER.info("Playing hard to win champion");
      this.champions += 1;
      LOGGER.info("Win one more champion, now the total champion is " + this.champions);
    }
  }

  /**
   * Update the address of the player.
   *
   * @param city  The city where the player lives
   * @param state The state where the city locates
   */
  public void updateAddress(String city, String state) {
    this.address.updateAddress(city, state);
  }
}
