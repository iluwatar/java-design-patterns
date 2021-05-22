package com.iluwatar.mapper;

/**
 * <p>It is the commodity class. It records the information of a commodity.</p>
 */
public final class Pricing {

  /**
   * Commodity's information.
   */
  private String name;
  private int id;
  private int price;
  private boolean leased = false;

  /**
   * Public constructor.
   *
   * @param name  commodity's name.
   * @param price commodity's price.
   */
  public Pricing(String name, int price) {
    this.name = name;
    this.price = price;
  }

  /**
   * Set commodity's name.
   *
   * @param name new name to be set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get commodity's name.
   *
   * @return the name of the commodity.
   */
  public String getName() {
    return name;
  }

  /**
   * Set commodity's price.
   *
   * @param price the price of the commodity.
   */
  public void setPrice(int price) {
    this.price = price;
  }

  /**
   * Get commodity's price.
   *
   * @return the price of the commodity.
   */
  public int getPrice() {
    return price;
  }

  /**
   * Set the commodity being leased.
   *
   * @param leased whether it is leased,
   */
  public void setLeased(boolean leased) {
    this.leased = leased;
  }

  /**
   * Get the commodity whether is leased.
   *
   * @return whether it is leased.
   */
  public boolean getLeased() {
    return leased;
  }

  /**
   * Set the id of the commodity.
   *
   * @param id the id of the commodity.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Get the id of the commodity.
   *
   * @return the id of the commodity.
   */
  public int getId() {
    return id;
  }
}
