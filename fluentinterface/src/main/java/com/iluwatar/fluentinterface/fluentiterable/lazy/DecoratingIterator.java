package com.iluwatar.fluentinterface.fluentiterable.lazy;

import java.util.Iterator;

/**
 * This class is used to realize LazyFluentIterables. It decorates a given iterator. Does not
 * support consecutive hasNext() calls.
 */
public abstract class DecoratingIterator<TYPE> implements Iterator<TYPE> {

  protected final Iterator<TYPE> fromIterator;

  private TYPE next = null;

  /**
   * Creates an iterator that decorates the given iterator.
   */
  public DecoratingIterator(Iterator<TYPE> fromIterator) {
    this.fromIterator = fromIterator;
  }

  /**
   * Precomputes and saves the next element of the Iterable. null is considered as end of data.
   * 
   * @return true if a next element is available
   */
  @Override
  public final boolean hasNext() {
    next = computeNext();
    return next != null;
  }

  /**
   * Returns the next element of the Iterable.
   * 
   * @return the next element of the Iterable, or null if not present.
   */
  @Override
  public final TYPE next() {
    if (next == null) {
      return fromIterator.next();
    } else {
      final TYPE result = next;
      next = null;
      return result;
    }
  }

  /**
   * Computes the next object of the Iterable. Can be implemented to realize custom behaviour for an
   * iteration process. null is considered as end of data.
   * 
   * @return the next element of the Iterable.
   */
  public abstract TYPE computeNext();
}
