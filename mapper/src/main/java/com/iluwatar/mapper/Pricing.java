package com.iluwatar.mapper;

/**
 * <p>It is the commodity class. It records the information of a commodity.</p>
 */
public final class Pricing {//NOPMD

  /**
   * Commodity's name.
   */
  private String name;

  /**
   * Commodity's id.
   */
  private int id;//NOPMD

  /**
   * Commodity's price.
   */
  private int price;

  /**
   * Commodity's leased.
   */
  private boolean leased;

  /**
   * Public constructor.
   *
   * @param name  commodity's name.
   * @param price commodity's price.
   */
  public Pricing(final String name, final int price) {
    this.name = name;
    this.price = price;
    this.leased = false;
  }

  /**
   * Set commodity's name.
   *
   * @param name new name to be set.
   */
  public void setName(final String name) {
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
  public void setPrice(final int price) {
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
  public void setLeased(final boolean leased) {
    this.leased = leased;
  }

  /**
   * Get the commodity whether is leased.
   *
   * @return whether it is leased.
   */
  public boolean isLeased() {
    return leased;
  }

  /**
   * Set the id of the commodity.
   *
   * @param id the id of the commodity.
   */
  public void setId(final int id) {//NOPMD
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
