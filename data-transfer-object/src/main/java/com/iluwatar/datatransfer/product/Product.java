/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.datatransfer.product;

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
