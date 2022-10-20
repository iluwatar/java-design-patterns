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

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

/**
* Currying decomposes a function with multiple arguments in multiple functions that
* take a single argument. A curried function which has only been passed some of its
* arguments is called a partial application. Partial application is useful since it can
* be used to create specialised functions in a concise way.
*
* <p>In this example, a librarian uses a curried book builder function create books belonging to
* desired genres and written by specific authors.
*/
@Slf4j
public class App {
  /**
  * Main entry point of the program.
  */
  public static void main(String[] args) {
    LOGGER.info("Librarian begins their work.");

    // Defining genre book functions
    Book.AddAuthor fantasyBookFunc = Book.builder().withGenre(Genre.FANTASY);
    Book.AddAuthor horrorBookFunc = Book.builder().withGenre(Genre.HORROR);
    Book.AddAuthor scifiBookFunc = Book.builder().withGenre(Genre.SCIFI);

    // Defining author book functions
    Book.AddTitle kingFantasyBooksFunc = fantasyBookFunc.withAuthor("Stephen King");
    Book.AddTitle kingHorrorBooksFunc = horrorBookFunc.withAuthor("Stephen King");
    Book.AddTitle rowlingFantasyBooksFunc = fantasyBookFunc.withAuthor("J.K. Rowling");

    // Creates books by Stephen King (horror and fantasy genres)
    Book shining = kingHorrorBooksFunc.withTitle("The Shining")
            .withPublicationDate(LocalDate.of(1977, 1, 28));
    Book darkTower = kingFantasyBooksFunc.withTitle("The Dark Tower: Gunslinger")
            .withPublicationDate(LocalDate.of(1982, 6, 10));

    // Creates fantasy books by J.K. Rowling
    Book chamberOfSecrets = rowlingFantasyBooksFunc.withTitle("Harry Potter and the Chamber of Secrets")
            .withPublicationDate(LocalDate.of(1998, 7, 2));

    // Create sci-fi books
    Book dune = scifiBookFunc.withAuthor("Frank Herbert")
            .withTitle("Dune")
            .withPublicationDate(LocalDate.of(1965, 8, 1));
    Book foundation = scifiBookFunc.withAuthor("Isaac Asimov")
            .withTitle("Foundation")
            .withPublicationDate(LocalDate.of(1942, 5, 1));

    LOGGER.info("Stephen King Books:");
    LOGGER.info(shining.toString());
    LOGGER.info(darkTower.toString());

    LOGGER.info("J.K. Rowling Books:");
    LOGGER.info(chamberOfSecrets.toString());

    LOGGER.info("Sci-fi Books:");
    LOGGER.info(dune.toString());
    LOGGER.info(foundation.toString());
  }
}