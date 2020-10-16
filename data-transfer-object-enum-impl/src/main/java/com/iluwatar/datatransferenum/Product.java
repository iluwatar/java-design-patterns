package com.iluwatar.datatransferenum;

/**
 * {@link Product} is a entity class for product entity. This class act as entity in the demo.
 */
public final class Product {
  private Long id;
  private String name;
  private Double price;
  private Double cost;
  private String supplier;

  /**
   * Constructor.
   *
   * @param id       product id
   * @param name     product name
   * @param price    product price
   * @param cost     product cost
   * @param supplier product supplier
   */
  public Product(Long id, String name, Double price, Double cost, String supplier) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.cost = cost;
    this.supplier = supplier;
  }

  /**
   * Constructor.
   */
  public Product() {
  }

  public Long getId() {
    return id;
  }

  public Product setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Product setName(String name) {
    this.name = name;
    return this;
  }

  public Double getPrice() {
    return price;
  }

  public Product setPrice(Double price) {
    this.price = price;
    return this;
  }

  public Double getCost() {
    return cost;
  }

  public Product setCost(Double cost) {
    this.cost = cost;
    return this;
  }

  public String getSupplier() {
    return supplier;
  }

  public Product setSupplier(String supplier) {
    this.supplier = supplier;
    return this;
  }

  @Override
  public String toString() {
    return "Product{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", price=" + price
        + ", cost=" + cost
        + ", supplier='" + supplier + '\''
        + '}';
  }
}
