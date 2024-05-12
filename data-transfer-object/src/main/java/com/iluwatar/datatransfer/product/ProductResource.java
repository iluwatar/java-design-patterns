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
package com.iluwatar.datatransfer.product;

import java.util.List;

/**
 * The resource class which serves product information. This class act as server in the demo. Which
 * has all product details.
 */
public record ProductResource(List<Product> products) {
  /**
   * Get all products.
   *
   * @return : all products in list but in the scheme of private dto.
   */
  public List<ProductDto.Response.Private> getAllProductsForAdmin() {
    return products
        .stream()
        .map(p -> new ProductDto.Response.Private().setId(p.getId()).setName(p.getName())
            .setCost(p.getCost())
            .setPrice(p.getPrice()))
        .toList();
  }

  /**
   * Get all products.
   *
   * @return : all products in list but in the scheme of public dto.
   */
  public List<ProductDto.Response.Public> getAllProductsForCustomer() {
    return products
        .stream()
        .map(p -> new ProductDto.Response.Public().setId(p.getId()).setName(p.getName())
            .setPrice(p.getPrice()))
        .toList();
  }

  /**
   * Save new product.
   *
   * @param createProductDto save new product to list.
   */
  public void save(ProductDto.Request.Create createProductDto) {
    products.add(Product.builder()
        .id((long) (products.size() + 1))
        .name(createProductDto.getName())
        .supplier(createProductDto.getSupplier())
        .price(createProductDto.getPrice())
        .cost(createProductDto.getCost())
        .build());
  }
}