package com.iluwatar.filter.criteria;

import java.util.List;

/**
 * Criteria interface which defines a filter criteria for a list of items.
 *
 * @param <T> the type of items that will be filtered
 */
public interface Criteria<T> {
  /**
   * Filters a list of items based on the criteria defined by this object.
   *
   * @param items the list of items to filter
   * @return list of items that meet the criteria defined by this object
   */
  public List<T> meetCriteria(List<T> items);
}
