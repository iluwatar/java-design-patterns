package com.iluwatar.combinator;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Complex finders consisting of simple finder.
 */
public class Finders {
  private Finders() {
  }


  /**
   * Finder to find a complex query.
   * @param query to find
   * @param orQuery alternative to find
   * @param notQuery exclude from search
   * @return new finder
   */
  public static Finder advancedFinder(String query, String orQuery, String notQuery) {
    return
        Finder.contains(query)
            .or(Finder.contains(orQuery))
            .not(Finder.contains(notQuery));
  }

  /**
   * Filtered finder looking a query with excluded queries as well.
   * @param query to find
   * @param excludeQueries to exclude
   * @return new finder
   */
  public static Finder filteredFinder(String query, String... excludeQueries) {
    var finder = Finder.contains(query);

    for (String q : excludeQueries) {
      finder = finder.not(Finder.contains(q));
    }
    return finder;

  }

  /**
   * Specialized query. Every next query is looked in previous result.
   * @param queries array with queries
   * @return new finder
   */
  public static Finder specializedFinder(String... queries) {
    var finder = identMult();

    for (String query : queries) {
      finder = finder.and(Finder.contains(query));
    }
    return finder;
  }

  /**
   * Expanded query. Looking for alternatives.
   * @param queries array with queries.
   * @return new finder
   */
  public static Finder expandedFinder(String... queries) {
    var finder = identSum();

    for (String query : queries) {
      finder = finder.or(Finder.contains(query));
    }
    return finder;
  }

  private static Finder identMult() {
    return txt -> Stream.of(txt.split("\n")).collect(Collectors.toList());
  }

  private static Finder identSum() {
    return txt -> new ArrayList<>();
  }
}
