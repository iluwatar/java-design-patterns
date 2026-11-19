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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CriterionTest {

  private static final Book DUNE =
      new Book("Dune", "Frank Herbert", Genre.SCIENCE_FICTION, 1965, 14.0);

  @Test
  void leafCriteriaCheckOneField() {
    assertTrue(genre(Genre.SCIENCE_FICTION).matches(DUNE));
    assertFalse(genre(Genre.FANTASY).matches(DUNE));
    assertTrue(author("Frank Herbert").matches(DUNE));
    assertTrue(publishedAfter(1964).matches(DUNE));
    assertFalse(publishedAfter(1965).matches(DUNE));
    assertTrue(priceAtMost(14.0).matches(DUNE));
    assertFalse(priceAtMost(13.9).matches(DUNE));
  }

  @Test
  void andRequiresBoth() {
    assertTrue(genre(Genre.SCIENCE_FICTION).and(priceAtMost(20)).matches(DUNE));
    assertFalse(genre(Genre.SCIENCE_FICTION).and(priceAtMost(10)).matches(DUNE));
  }

  @Test
  void orRequiresEither() {
    assertTrue(genre(Genre.FANTASY).or(priceAtMost(20)).matches(DUNE));
    assertFalse(genre(Genre.FANTASY).or(priceAtMost(10)).matches(DUNE));
  }

  @Test
  void notNegates() {
    assertFalse(genre(Genre.SCIENCE_FICTION).not().matches(DUNE));
    assertTrue(genre(Genre.FANTASY).not().matches(DUNE));
  }

  @Test
  void nestedCombinationIsEvaluatedAndDescribed() {
    var criterion =
        genre(Genre.FANTASY).or(genre(Genre.SCIENCE_FICTION)).and(author("Frank Herbert").not());
    assertFalse(criterion.matches(DUNE));
    assertEquals(
        "((genre = FANTASY OR genre = SCIENCE_FICTION) AND NOT author = 'Frank Herbert')",
        criterion.describe());
  }

  @Test
  void leafCriterionDerivesCheckAndDescriptionFromItsData() {
    var criterion = Criterion.of("title", Operator.EQUALS, "Dune", Book::title);
    assertTrue(criterion.matches(DUNE));
    assertEquals("title = 'Dune'", criterion.describe());
    assertEquals("year > 1964", publishedAfter(1964).describe());
    assertEquals("price <= 14.0", priceAtMost(14.0).describe());
  }

  @Test
  void operatorsCompareValues() {
    assertTrue(Operator.EQUALS.test(3, 3));
    assertFalse(Operator.EQUALS.test(3, 4));
    assertTrue(Operator.GREATER_THAN.test(4, 3));
    assertFalse(Operator.GREATER_THAN.test(3, 3));
    assertTrue(Operator.AT_MOST.test(3, 3));
    assertFalse(Operator.AT_MOST.test(4, 3));
  }
}
