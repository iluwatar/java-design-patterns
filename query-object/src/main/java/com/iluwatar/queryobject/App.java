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
package com.iluwatar.queryobject;

import static com.iluwatar.queryobject.BookCriteria.author;
import static com.iluwatar.queryobject.BookCriteria.genre;
import static com.iluwatar.queryobject.BookCriteria.priceAtMost;
import static com.iluwatar.queryobject.BookCriteria.publishedAfter;

import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * The Query Object pattern represents a query as an object. Instead of writing a new finder method
 * for every combination of conditions, the caller builds a {@link Query} from {@link Criterion}
 * objects, changes it as needed and hands it to a data source to execute.
 *
 * <p>This example builds a query over a small library catalog, then adds, removes and combines
 * criteria and runs the same query against two different catalogs.
 */
@Slf4j
public class App {

  /** Program entry point. */
  public static void main(String[] args) {
    var catalog = catalog();

    var query =
        new Query<Book>()
            .where("genre", genre(Genre.FANTASY).or(genre(Genre.SCIENCE_FICTION)))
            .where("price", priceAtMost(15.0))
            .orderBy("year", Comparator.comparingInt(Book::year));
    log(query, catalog);

    query.where("recent", publishedAfter(1960));
    log(query, catalog);

    query.remove("price").where("author", author("Ursula K. Le Guin").not()).limit(2);
    log(query, catalog);

    var secondHandShop =
        List.of(
            new Book("Foundation", "Isaac Asimov", Genre.SCIENCE_FICTION, 1951, 4.0),
            new Book("Neuromancer", "William Gibson", Genre.SCIENCE_FICTION, 1984, 6.0),
            new Book("The Colour of Magic", "Terry Pratchett", Genre.FANTASY, 1983, 3.5));
    LOGGER.info("Same query against another catalog:");
    log(query, secondHandShop);
  }

  /** A small catalog of books to query. */
  static List<Book> catalog() {
    return List.of(
        new Book("The Hobbit", "J.R.R. Tolkien", Genre.FANTASY, 1937, 12.5),
        new Book("Dune", "Frank Herbert", Genre.SCIENCE_FICTION, 1965, 14.0),
        new Book("A Wizard of Earthsea", "Ursula K. Le Guin", Genre.FANTASY, 1968, 9.9),
        new Book(
            "The Left Hand of Darkness", "Ursula K. Le Guin", Genre.SCIENCE_FICTION, 1969, 18.0),
        new Book("Hyperion", "Dan Simmons", Genre.SCIENCE_FICTION, 1989, 16.5),
        new Book("The Name of the Rose", "Umberto Eco", Genre.MYSTERY, 1980, 11.0),
        new Book("SPQR", "Mary Beard", Genre.HISTORY, 2015, 20.0));
  }

  private static void log(Query<Book> query, List<Book> books) {
    LOGGER.info("{}", query.describe());
    query.execute(books).forEach(book -> LOGGER.info("  {} ({})", book.title(), book.year()));
  }
}
