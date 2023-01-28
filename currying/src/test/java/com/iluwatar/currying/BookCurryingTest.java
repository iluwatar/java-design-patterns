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
package com.iluwatar.currying;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

/**
 * Unit tests for the Book class
 */
class BookCurryingTest {
  private static Book expectedBook;

  @BeforeAll
  public static void initialiseBook() {
    expectedBook = new Book(Genre.FANTASY,
        "Dave",
        "Into the Night",
        LocalDate.of(2002, 4, 7));
  }

  /**
   * Tests that the expected book can be created via curried functions
   */
  @Test
  void createsExpectedBook() {
    Book builderCurriedBook = Book.builder()
        .withGenre(Genre.FANTASY)
        .withAuthor("Dave")
        .withTitle("Into the Night")
        .withPublicationDate(LocalDate.of(2002, 4, 7));

    Book funcCurriedBook = Book.book_creator
            .apply(Genre.FANTASY)
            .apply("Dave")
            .apply("Into the Night")
            .apply(LocalDate.of(2002, 4, 7));

    assertEquals(expectedBook, builderCurriedBook);
    assertEquals(expectedBook, funcCurriedBook);
  }

  /**
   * Tests that an intermediate curried function can be used to create the expected book
   */
  @Test
  void functionCreatesExpectedBook() {
    Book.AddTitle daveFantasyBookFunc = Book.builder()
      .withGenre(Genre.FANTASY)
      .withAuthor("Dave");

    Book curriedBook = daveFantasyBookFunc.withTitle("Into the Night")
      .withPublicationDate(LocalDate.of(2002, 4, 7));

    assertEquals(expectedBook, curriedBook);
  }
}
