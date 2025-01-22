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
package com.iluwatar;

import java.util.logging.Logger;

/*
 * The Two Step View Pattern:
 * Separates generating dynamic web content into:
 * 1. Data Preparation: Raw data is structured for presentation.
 * 2. Data Presentation: Prepared data is rendered into a display format (e.g., HTML).
 * This enhances modularity, maintainability, and testability by decoupling preparation and presentation.

 * Implementation in this Example:
 * 1. Data Preparation: `DataPreparation` transforms a `Book` into a `BookStore` object, calculating discount prices and stock status.
 * 2. Data Presentation: `Presentation` generates an HTML view of the `BookStore` object, focusing only on rendering.
 */

/** Main class. */
public class App {
  private static final Logger logger = Logger.getLogger(App.class.getName());

  /** Main function. */
  public static void main(String[] args) {
    // Create a Book instance with sample data
    Book book = new Book("Batman Vol. 1: The Court of Owls", 11.60, true, 10);

    // Convert raw book data into a structured format
    BookStore preparedData = DataPreparation.prepareBook(book);

    // Converts the prepared book data into an HTML string for display
    String htmlOutput = Presentation.presentBook(preparedData);

    // Output the rendered HTML
    logger.info(htmlOutput);
  }
}

