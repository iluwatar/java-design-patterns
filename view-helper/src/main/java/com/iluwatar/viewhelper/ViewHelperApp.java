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
package com.iluwatar.viewhelper;

import java.util.logging.Logger;

/**
 * The main application class for demonstrating the View Helper pattern.
 */
public final class ViewHelperApp {

  private static final Logger LOGGER = Logger.getLogger(ViewHelperApp.class.getName());
  private static final int AGE = 30;
  private static final double PRICE = 999.99;

  private ViewHelperApp() {
    // Prevent instantiation
  }

  /**
   * The entry point of the application.
   *
   * @param args command line arguments (not used)
   */
  public static void main(String... args) {
    User user = new User("John Doe", AGE, "john.doe@example.com");
    Product product = new Product(1, "Laptop", PRICE);

    // Conditionally log the user details with string formatting
    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      LOGGER.info(String.format("User details: Name = %s, Age = %d, Email = %s",
          user.getName(), user.getAge(), user.getEmail()));
    }

    // Conditionally log the product details with string formatting
    if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
      LOGGER.info(String.format("Product details: ID = %d, Name = %s, Price = %.2f",
          product.getId(), product.getName(), product.getPrice()));
    }
  }
}
