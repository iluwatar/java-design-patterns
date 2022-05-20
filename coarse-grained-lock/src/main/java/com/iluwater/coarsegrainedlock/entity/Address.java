package com.iluwater.coarsegrainedlock.entity;

import lombok.extern.slf4j.Slf4j;

/**
 * Address class
 * Store the address of a certain player.
 */
@Slf4j
public class Address {
  private final Player player;
  private String city;
  private String state;

  /**
   * Construct function.
   *
   * @param player Instance of Player Class
   * @param city   The city where the player lives
   * @param state  The state where the city locates
   */
  public Address(Player player, String city, String state) {
    this.player = player;
    this.city = city;
    this.state = state;
  }

  /**
   * Get function of city.
   *
   * @return city
   */
  public String getCity() {
    return city;
  }

  /**
   * Get function os state.
   *
   * @return state
   */
  public String getState() {
    return state;
  }

  /**
   * Update the address of the player.
   *
   * @param city The city which the player would move to
   * @param state The state where the city locates
   */
  public void updateAddress(String city, String state) {
    synchronized (player.getMutex()) {
      LOGGER.info("Become Free Agent");
      this.city = city;
      this.state = state;
      LOGGER.info(player.getFirstName() + " " + player.getLastName() + " "
          + "brings talent to " + this.city + ", which is in " + this.state);
    }
  }

}
