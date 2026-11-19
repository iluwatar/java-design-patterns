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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

class QueryTest {

  private final List<Book> catalog = App.catalog();

  private static List<String> titles(List<Book> books) {
    return books.stream().map(Book::title).toList();
  }

  @Test
  void emptyQueryReturnsEverything() {
    assertEquals(catalog, new Query<Book>().execute(catalog));
    assertEquals("SELECT *", new Query<Book>().describe());
  }

  @Test
  void allCriteriaMustMatch() {
    var query =
        new Query<Book>().where("genre", genre(Genre.FANTASY)).where("price", priceAtMost(10));
    assertEquals(List.of("A Wizard of Earthsea"), titles(query.execute(catalog)));
  }

  @Test
  void addingCriteriaNarrowsAndRemovingWidensTheResult() {
    var query = new Query<Book>().where("genre", genre(Genre.SCIENCE_FICTION));
    assertEquals(3, query.execute(catalog).size());

    query.where("price", priceAtMost(15));
    assertEquals(List.of("Dune"), titles(query.execute(catalog)));

    query.remove("price");
    assertEquals(3, query.execute(catalog).size());
  }

  @Test
  void removingUnknownCriterionDoesNothing() {
    var query = new Query<Book>().where("genre", genre(Genre.MYSTERY)).remove("missing");
    assertEquals(List.of("The Name of the Rose"), titles(query.execute(catalog)));
  }

  @Test
  void criterionWithSameNameReplacesTheOldOne() {
    var query =
        new Query<Book>().where("genre", genre(Genre.MYSTERY)).where("genre", genre(Genre.HISTORY));
    assertEquals(List.of("SPQR"), titles(query.execute(catalog)));
  }

  @Test
  void combinedCriteriaWorkInsideAQuery() {
    var query =
        new Query<Book>()
            .where("genre", genre(Genre.FANTASY).or(genre(Genre.SCIENCE_FICTION)))
            .where("author", author("Ursula K. Le Guin").not())
            .where("recent", publishedAfter(1960));
    assertEquals(List.of("Dune", "Hyperion"), titles(query.execute(catalog)));
  }

  @Test
  void orderAndLimitAreApplied() {
    var query =
        new Query<Book>()
            .where("genre", genre(Genre.SCIENCE_FICTION))
            .orderBy("price DESC", Comparator.comparingDouble(Book::price).reversed())
            .limit(2);
    assertEquals(List.of("The Left Hand of Darkness", "Hyperion"), titles(query.execute(catalog)));
    assertEquals(
        "SELECT * WHERE genre = SCIENCE_FICTION ORDER BY price DESC LIMIT 2", query.describe());
  }

  @Test
  void negativeLimitIsRejected() {
    var query = new Query<Book>();
    assertThrows(IllegalArgumentException.class, () -> query.limit(-1));
  }

  @Test
  void sameQueryCanBeReusedOnDifferentSourcesWithoutChangingThem() {
    var query = new Query<Book>().where("genre", genre(Genre.FANTASY));
    var other =
        new ArrayList<>(
            List.of(new Book("Mort", "Terry Pratchett", Genre.FANTASY, 1987, 5.0), catalog.get(1)));
    var otherBefore = List.copyOf(other);

    assertEquals(List.of("The Hobbit", "A Wizard of Earthsea"), titles(query.execute(catalog)));
    assertEquals(List.of("Mort"), titles(query.execute(other)));
    assertEquals(List.of("The Hobbit", "A Wizard of Earthsea"), titles(query.execute(catalog)));
    assertEquals(otherBefore, other);
  }
}
