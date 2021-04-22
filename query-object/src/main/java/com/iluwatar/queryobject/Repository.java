package com.iluwatar.queryobject;

import java.util.Collection;
import java.util.stream.Collectors;


/**
 * The class is a data repository. In real scenarios this repository
 * can be a abstraction of DBMS created by ORM which allows you to
 * retrieve and query data through it.
 *
 * @param <T> The type of object it contains.
 */
public class Repository<T> {
  public final Collection<T> dbContext;

  public Repository(Collection<T> dbContext) {
    this.dbContext = dbContext;
  }

  /**
   * This method accepts variable numbers of query objects, and then execute a
   * query that applies the conjunction of all those conditions described by
   * the lambda expression in {@link QueryObject} instances as the final condition.
   *
   * @param criteria An array of query objects.
   * @return The result subset that fits all the conditions in arguments.
   */
  @SafeVarargs
  public final Collection<T> query(QueryObject<T>... criteria) {
    var initStream = dbContext.stream();
    for (var criterion : criteria) {
      initStream = initStream.filter(criterion.query());
    }
    return initStream.collect(Collectors.toList());
  }
}
