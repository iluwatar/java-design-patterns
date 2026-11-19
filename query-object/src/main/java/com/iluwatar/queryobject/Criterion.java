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

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A single condition of a {@link Query}. A leaf criterion is built from structured data: a field,
 * an {@link Operator} and a value. Both its check and its SQL-like description are derived from
 * that data, which is how a query object is translated into the language of a real data source.
 * Criteria can be combined with {@link #and}, {@link #or} and {@link #not} into larger criteria.
 *
 * @param <T> the type of object the criterion is checked against
 */
public interface Criterion<T> {

  /** Check whether the given object satisfies this criterion. */
  boolean matches(T candidate);

  /** Describe this criterion in a readable, SQL-like form. */
  String describe();

  /**
   * Create a leaf criterion that compares one field of the candidate with a value. The check and
   * the description are both derived from the field, the operator and the value.
   *
   * @param field the name of the field in the query language
   * @param operator how the field is compared with the value
   * @param value the value the field is compared with
   * @param extractor reads the field from a candidate
   */
  static <T, V extends Comparable<? super V>> Criterion<T> of(
      String field, Operator operator, V value, Function<T, V> extractor) {
    var literal = value instanceof String ? "'" + value + "'" : String.valueOf(value);
    return of(
        field + " " + operator.symbol() + " " + literal,
        candidate -> operator.test(extractor.apply(candidate), value));
  }

  /** Create a criterion from a description and a predicate; used to combine criteria. */
  static <T> Criterion<T> of(String description, Predicate<T> predicate) {
    return new Criterion<>() {
      @Override
      public boolean matches(T candidate) {
        return predicate.test(candidate);
      }

      @Override
      public String describe() {
        return description;
      }
    };
  }

  /** Combine this criterion with another one; both must match. */
  default Criterion<T> and(Criterion<T> other) {
    return of(
        "(" + describe() + " AND " + other.describe() + ")",
        candidate -> matches(candidate) && other.matches(candidate));
  }

  /** Combine this criterion with another one; at least one must match. */
  default Criterion<T> or(Criterion<T> other) {
    return of(
        "(" + describe() + " OR " + other.describe() + ")",
        candidate -> matches(candidate) || other.matches(candidate));
  }

  /** Negate this criterion. */
  default Criterion<T> not() {
    return of("NOT " + describe(), candidate -> !matches(candidate));
  }
}
