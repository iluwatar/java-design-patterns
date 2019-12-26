/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.fluentinterface.fluentiterable.simple;

import com.iluwatar.fluentinterface.fluentiterable.FluentIterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This is a simple implementation of the FluentIterable interface. It evaluates all chained
 * operations eagerly. This implementation would be costly to be utilized in real applications.
 *
 * @param <E> the type of the objects the iteration is about
 */
public class SimpleFluentIterable<E> implements FluentIterable<E> {

  private final Iterable<E> iterable;

  /**
   * This constructor creates a copy of a given iterable's contents.
   *
   * @param iterable the iterable this interface copies to work on.
   */
  protected SimpleFluentIterable(Iterable<E> iterable) {
    this.iterable = iterable;
  }

  /**
   * Filters the contents of Iterable using the given predicate, leaving only the ones which satisfy
   * the predicate.
   *
   * @param predicate the condition to test with for the filtering. If the test is negative, the
   *                  tested object is removed by the iterator.
   * @return the same FluentIterable with a filtered collection
   */
  @Override
  public final FluentIterable<E> filter(Predicate<? super E> predicate) {
    var iterator = iterator();
    while (iterator.hasNext()) {
      var nextElement = iterator.next();
      if (!predicate.test(nextElement)) {
        iterator.remove();
      }
    }
    return this;
  }

  /**
   * Can be used to collect objects from the Iterable. Is a terminating operation.
   *
   * @return an option of the first object of the Iterable
   */
  @Override
  public final Optional<E> first() {
    var resultIterator = first(1).iterator();
    return resultIterator.hasNext() ? Optional.of(resultIterator.next()) : Optional.empty();
  }

  /**
   * Can be used to collect objects from the Iterable. Is a terminating operation.
   *
   * @param count defines the number of objects to return
   * @return the same FluentIterable with a collection decimated to a maximum of 'count' first
   *     objects.
   */
  @Override
  public final FluentIterable<E> first(int count) {
    var iterator = iterator();
    var currentCount = 0;
    while (iterator.hasNext()) {
      iterator.next();
      if (currentCount >= count) {
        iterator.remove();
      }
      currentCount++;
    }
    return this;
  }

  /**
   * Can be used to collect objects from the Iterable. Is a terminating operation.
   *
   * @return an option of the last object of the Iterable
   */
  @Override
  public final Optional<E> last() {
    var list = last(1).asList();
    if (list.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(list.get(0));
  }

  /**
   * Can be used to collect objects from the Iterable. Is a terminating operation.
   *
   * @param count defines the number of objects to return
   * @return the same FluentIterable with a collection decimated to a maximum of 'count' last
   *     objects
   */
  @Override
  public final FluentIterable<E> last(int count) {
    var remainingElementsCount = getRemainingElementsCount();
    var iterator = iterator();
    var currentIndex = 0;
    while (iterator.hasNext()) {
      iterator.next();
      if (currentIndex < remainingElementsCount - count) {
        iterator.remove();
      }
      currentIndex++;
    }

    return this;
  }

  /**
   * Transforms this FluentIterable into a new one containing objects of the type T.
   *
   * @param function a function that transforms an instance of E into an instance of T
   * @param <T>      the target type of the transformation
   * @return a new FluentIterable of the new type
   */
  @Override
  public final <T> FluentIterable<T> map(Function<? super E, T> function) {
    var temporaryList = new ArrayList<T>();
    this.forEach(e -> temporaryList.add(function.apply(e)));
    return from(temporaryList);
  }

  /**
   * Collects all remaining objects of this Iterable into a list.
   *
   * @return a list with all remaining objects of this Iterable
   */
  @Override
  public List<E> asList() {
    return toList(iterable.iterator());
  }

  /**
   * Constructs FluentIterable from iterable.
   *
   * @return a FluentIterable from a given iterable. Calls the SimpleFluentIterable constructor.
   */
  public static <E> FluentIterable<E> from(Iterable<E> iterable) {
    return new SimpleFluentIterable<>(iterable);
  }

  public static <E> FluentIterable<E> fromCopyOf(Iterable<E> iterable) {
    var copy = FluentIterable.copyToList(iterable);
    return new SimpleFluentIterable<>(copy);
  }

  @Override
  public Iterator<E> iterator() {
    return iterable.iterator();
  }

  @Override
  public void forEach(Consumer<? super E> action) {
    iterable.forEach(action);
  }


  @Override
  public Spliterator<E> spliterator() {
    return iterable.spliterator();
  }

  /**
   * Find the count of remaining objects of current iterable.
   *
   * @return the count of remaining objects of the current Iterable
   */
  public final int getRemainingElementsCount() {
    var counter = 0;
    for (var ignored : this) {
      counter++;
    }
    return counter;
  }

  /**
   * Collects the remaining objects of the given iterator into a List.
   *
   * @return a new List with the remaining objects.
   */
  public static <E> List<E> toList(Iterator<E> iterator) {
    var copy = new ArrayList<E>();
    iterator.forEachRemaining(copy::add);
    return copy;
  }
}
