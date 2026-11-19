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

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A query represented as an object. Criteria are added and removed by name, so the query can be
 * built up step by step, changed later and reused. Construction is separate from execution: the
 * query does nothing until {@link #execute} runs it against a data source, here a collection of
 * objects held in memory.
 *
 * @param <T> the type of object the query selects
 */
public class Query<T> {

  private final Map<String, Criterion<T>> criteria = new LinkedHashMap<>();
  private String orderDescription;
  private Comparator<T> order;
  private Integer limit;

  /**
   * Add a named criterion. All criteria of a query must match. Adding a criterion under a name that
   * is already used replaces the old one.
   */
  public Query<T> where(String name, Criterion<T> criterion) {
    criteria.put(name, criterion);
    return this;
  }

  /** Remove the criterion with the given name; removing an unknown name does nothing. */
  public Query<T> remove(String name) {
    criteria.remove(name);
    return this;
  }

  /** Sort the results; the description names the sort key when the query is described. */
  public Query<T> orderBy(String description, Comparator<T> comparator) {
    this.orderDescription = description;
    this.order = comparator;
    return this;
  }

  /** Return at most the given number of results. */
  public Query<T> limit(int maxResults) {
    if (maxResults < 0) {
      throw new IllegalArgumentException("limit must not be negative: " + maxResults);
    }
    this.limit = maxResults;
    return this;
  }

  /** Run the query against the given source. The source is left unchanged. */
  public List<T> execute(Collection<T> source) {
    var results = source.stream().filter(this::matchesAll);
    if (order != null) {
      results = results.sorted(order);
    }
    if (limit != null) {
      results = results.limit(limit);
    }
    return results.toList();
  }

  /** Describe the whole query in a readable, SQL-like form. */
  public String describe() {
    var description = new StringBuilder("SELECT *");
    if (!criteria.isEmpty()) {
      description
          .append(" WHERE ")
          .append(
              criteria.values().stream()
                  .map(Criterion::describe)
                  .collect(Collectors.joining(" AND ")));
    }
    Optional.ofNullable(orderDescription)
        .ifPresent(o -> description.append(" ORDER BY ").append(o));
    Optional.ofNullable(limit).ifPresent(l -> description.append(" LIMIT ").append(l));
    return description.toString();
  }

  private boolean matchesAll(T candidate) {
    return criteria.values().stream().allMatch(criterion -> criterion.matches(candidate));
  }
}
