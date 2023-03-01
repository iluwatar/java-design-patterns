/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.filter.product;

/**
 * Class for a product.
 */
public class Product implements Comparable<Product> {
  /** Name of product. */
  private String productName;

  /** Price of product. */
  private int price;

  /** Category of product. */
  private ProductCategory category;

  /** Number of items of this product in stock. */
  private int nrInStock;

  /**
   * Constructor for a product object.
   * 
   * @param productName the name of the product
   * @param price       the price of the product
   * @param category    the category of the product
   * @param nrInStock   number of items in stock of this product
   */
  public Product(String productName, int price, ProductCategory category, int nrInStock) {
    this.productName = productName;
    this.price = price;
    this.category = category;
    this.nrInStock = nrInStock;
  }

  /**
   * Returns the name of the product.
   * 
   * @return the name of the product.
   */
  public String getProductName() {
    return this.productName;
  }

  /**
   * Returns the price of the product.
   * 
   * @return the price of the product.
   */
  public int getPrice() {
    return this.price;
  }

  /**
   * Returns the category of the product.
   * 
   * @return the category of the product.
   */
  public ProductCategory getCategory() {
    return this.category;
  }

  /**
   * Returns the number of items of the product in stock.
   * 
   * @return the number of items of the product in stock.
   */
  public int getNrInStock() {
    return this.nrInStock;
  }

  /**
   * Compares this product with the specified product for order based on the
   * product name.
   * 
   * @param other the product to be compared
   * @return a negative integer, zero, or a positive integer as this product is
   *         less than, equal to, or greater than the specified product
   */
  @Override
  public int compareTo(Product other) {
    return this.productName.compareTo(other.productName);
  }

}
