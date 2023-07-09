package com.iluwatar.api;

/**
 * Service for entity update.
 *
 * @param <T> target entity
 */
public interface UpdateService<T> {

  /**
   * Update entity.
   *
   * @param obj entity to update
   * @param id  primary key
   * @return modified entity
   */
  T doUpdate(T obj, long id);
}
