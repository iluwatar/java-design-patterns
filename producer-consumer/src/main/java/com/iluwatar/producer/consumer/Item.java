package com.iluwatar.producer.consumer;

/**
 * Class take part of an {@link Producer}-{@link Consumer} exchange.
 */
public class Item {

  private String producer;

  private int id;

  public Item(String producer, int id) {
    this.id = id;
    this.producer = producer;
  }

  public int getId() {

    return id;
  }

  public String getProducer() {

    return producer;
  }

}
