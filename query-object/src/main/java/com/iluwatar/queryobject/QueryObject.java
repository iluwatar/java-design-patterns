package com.iluwatar.queryobject;

import java.util.function.Predicate;

/**
 * This is the interface of any criterion of an instance of any given type. Implement the
 * query() method to describe the criterion of your instance.
 *
 * @param <T> The type of instance that this criterion is judging.
 */
public interface QueryObject<T> {
  Predicate<T> query();
}
