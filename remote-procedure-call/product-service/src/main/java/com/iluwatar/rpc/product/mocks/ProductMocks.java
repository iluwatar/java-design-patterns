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
package com.iluwatar.rpc.product.mocks;

import com.iluwatar.rpc.product.model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Mocks data for product POJO, used for testing and in memory store.
 * Thread safety is not guaranteed.
 * @link com.iluwatar.rpc.product.model.Product
 * @author CoderSleek
 * @version 1.0
 */
public class ProductMocks {
  /**
   * Returns new Mock product ArrayList on each call, to reset state.
   *
   * @return the mock products
   */
  public static List<Product> getMockProducts() {
    return new ArrayList<>() {{
        add(new Product(1L, "Product 1", "Type 1", 50.0, 10));
        add(new Product(2L, "Product 2", "Type 2", 40.0, 20));
        add(new Product(3L, "Product 3",  "Type 3", 30.0, 30));
        add(new Product(4L, "Product 4", "Type 4", 20.0, 40));
        add(new Product(5L, "Product 5", "Type 5", 10.0, 50));
      }
    };
  }
}