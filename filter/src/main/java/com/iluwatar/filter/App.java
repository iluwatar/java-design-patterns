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
package com.iluwatar.filter;

import com.iluwatar.filter.criteria.AndCriteria;
import com.iluwatar.filter.criteria.Criteria;
import com.iluwatar.filter.criteria.InStockCriteria;
import com.iluwatar.filter.criteria.MediumPriceCriteria;
import com.iluwatar.filter.product.Product;
import com.iluwatar.filter.product.ProductCategory;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class.
 * Demonstrates how two filters can be used together using the and filter.
 */
public class App {
  /**
   * Program entry point.
   * @param args runtime arguments.
   */
  public static void main(String[] args) {
    Criteria<Product> inStockCriteria = new InStockCriteria();
    Criteria<Product> mediumPriceCriteria = new MediumPriceCriteria();

    List<Criteria> criteria = new ArrayList<>();
    criteria.add(inStockCriteria);
    criteria.add(mediumPriceCriteria);

    AndCriteria andCriteria = new AndCriteria(criteria);

    Product jeans = new Product("Jeans", 1000, ProductCategory.CLOTHING, 4);
    Product shirt = new Product("Shirt", 400, ProductCategory.CLOTHING, 0);
    Product shoes = new Product("Shoes", 600, ProductCategory.CLOTHING, 2);
    List<Product> products = new ArrayList<>();
    products.add(jeans);
    products.add(shirt);
    products.add(shoes);

    List<Product> mediumPriceInStock = andCriteria.meetCriteria(products);

    // mediumPriceInStock contains Shoes, shirt is not in stock and jeans does not meet the price criteria.
    System.out.println("Products in stock:");
    for (Product p : mediumPriceInStock) {
      System.out.println(p.getProductName());
    }
  }
}
