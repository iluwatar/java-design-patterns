package com.iluwatar.cqrs.dto;

public class BookDTO {

  private String title;
  private double price;

  public BookDTO(String title, double price) {
    super();
    this.title = title;
    this.price = price;
  }

  public BookDTO() {
    super();
  }

  public String getTitle() {
    return title;
  }

  public double getPrice() {
    return price;
  }

}
