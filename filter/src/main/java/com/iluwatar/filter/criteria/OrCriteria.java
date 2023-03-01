package com.iluwatar.filter.criteria;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Or criteria implementation, which creates a filtering criteria
 * by applying the OR operator, and applies it to a list of items, returning
 * the items that satisfy any of the criteria.
 * 
 * @param <T> the type of the items to filter
 */
public class OrCriteria<T> implements Criteria<T> {

  /** The first criteria of the OR operator. */
  private Criteria<T> firstCriteria;

  /** The second criteria of the OR operator. */
  private Criteria<T> secondCriteria;

  /**
   * Constructor.
   * 
   * @param firstCriteria  the first criteria in the union
   * @param secondCriteria the second criteria in the union
   */
  public OrCriteria(Criteria<T> firstCriteria, Criteria<T> secondCriteria) {
    this.firstCriteria = firstCriteria;
    this.secondCriteria = secondCriteria;
  }

  /**
   * Filters a list of items based on the union of two criteria.
   * 
   * @param items the list of items to filter
   * @return list of items that meet the criteria of either the first or second
   *         criteria
   */
  @Override
  public List<T> meetCriteria(List<T> items) {
    Set<T> union = new HashSet<>(firstCriteria.meetCriteria(items));
    union.addAll(secondCriteria.meetCriteria(items));
    return new ArrayList<T>(union);
  }
}