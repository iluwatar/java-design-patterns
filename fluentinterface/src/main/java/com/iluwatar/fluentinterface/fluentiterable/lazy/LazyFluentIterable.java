/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.fluentinterface.fluentiterable.lazy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import com.iluwatar.fluentinterface.fluentiterable.FluentIterable;

/**
 * This is a lazy implementation of the FluentIterable interface. It evaluates all chained
 * operations when a terminating operation is applied.
 * 
 * @param <E> the type of the objects the iteration is about
 */
public class LazyFluentIterable<E> implements FluentIterable<E> {

  private final Iterable<E> iterable;

  /**
   * This constructor creates a new LazyFluentIterable. It wraps the given iterable.
   * 
   * @param iterable the iterable this FluentIterable works on.
   */
  protected LazyFluentIterable(Iterable<E> iterable) {
    this.iterable = iterable;
  }

  /**
   * This constructor can be used to implement anonymous subclasses of the LazyFluentIterable.
   */
  protected LazyFluentIterable() {
    iterable = this;
  }

  /**
   * Filters the contents of Iterable using the given predicate, leaving only the ones which satisfy
   * the predicate.
   * 
   * @param predicate the condition to test with for the filtering. If the test is negative, the
   *        tested object is removed by the iterator.
   * @return a new FluentIterable object that decorates the source iterable
   */
  @Override
  public FluentIterable<E> filter(Predicate<? super E> predicate) {
    return new LazyFluentIterable<E>() {
      @Override
      public Iterator<E> iterator() {
        return new DecoratingIterator<E>(iterable.iterator()) {
          @Override
          public E computeNext() {
            while (fromIterator.hasNext()) {
              E candidate = fromIterator.next();
              if (predicate.test(candidate)) {
                return candidate;
              }
            }

            return null;
          }
        };
      }
    };
  }

  /**
   * Can be used to collect objects from the iteration. Is a terminating operation.
   * 
   * @return an Optional containing the first object of this Iterable
   */
  @Override
  public Optional<E> first() {
    Iterator<E> resultIterator = first(1).iterator();
    return resultIterator.hasNext() ? Optional.of(resultIterator.next()) : Optional.empty();
  }

  /**
   * Can be used to collect objects from the iteration.
   * 
   * @param count defines the number of objects to return
   * @return the same FluentIterable with a collection decimated to a maximum of 'count' first
   *         objects.
   */
  @Override
  public FluentIterable<E> first(int count) {
    return new LazyFluentIterable<E>() {
      @Override
      public Iterator<E> iterator() {
        return new DecoratingIterator<E>(iterable.iterator()) {
          int currentIndex;

          @Override
          public E computeNext() {
            if (currentIndex < count && fromIterator.hasNext()) {
              E candidate = fromIterator.next();
              currentIndex++;
              return candidate;
            }
            return null;
          }
        };
      }
    };
  }

  /**
   * Can be used to collect objects from the iteration. Is a terminating operation.
   * 
   * @return an Optional containing the last object of this Iterable
   */
  @Override
  public Optional<E> last() {
    Iterator<E> resultIterator = last(1).iterator();
    return resultIterator.hasNext() ? Optional.of(resultIterator.next()) : Optional.empty();
  }

  /**
   * Can be used to collect objects from the Iterable. Is a terminating operation. This operation is
   * memory intensive, because the contents of this Iterable are collected into a List, when the
   * next object is requested.
   * 
   * @param count defines the number of objects to return
   * @return the same FluentIterable with a collection decimated to a maximum of 'count' last
   *         objects
   */
  @Override
  public FluentIterable<E> last(int count) {
    return new LazyFluentIterable<E>() {
      @Override
      public Iterator<E> iterator() {
        return new DecoratingIterator<E>(iterable.iterator()) {
          private int stopIndex;
          private int totalElementsCount;
          private List<E> list;
          private int currentIndex;

          @Override
          public E computeNext() {
            initialize();

            E candidate = null;
            while (currentIndex < stopIndex && fromIterator.hasNext()) {
              currentIndex++;
              fromIterator.next();
            }
            if (currentIndex >= stopIndex && fromIterator.hasNext()) {
              candidate = fromIterator.next();
            }
            return candidate;
          }

          private void initialize() {
            if (list == null) {
              list = new ArrayList<>();
              Iterator<E> newIterator = iterable.iterator();
              while (newIterator.hasNext()) {
                list.add(newIterator.next());
              }

              totalElementsCount = list.size();
              stopIndex = totalElementsCount - count;
            }
          }
        };
      }
    };
  }

  /**
   * Transforms this FluentIterable into a new one containing objects of the type T.
   * 
   * @param function a function that transforms an instance of E into an instance of T
   * @param <T> the target type of the transformation
   * @return a new FluentIterable of the new type
   */
  @Override
  public <T> FluentIterable<T> map(Function<? super E, T> function) {
    return new LazyFluentIterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return new DecoratingIterator<T>(null) {
          Iterator<E> oldTypeIterator = iterable.iterator();

          @Override
          public T computeNext() {
            if (oldTypeIterator.hasNext()) {
              E candidate = oldTypeIterator.next();
              return function.apply(candidate);
            } else {
              return null;
            }
          }
        };
      }
    };
  }

  /**
   * Collects all remaining objects of this iteration into a list.
   * 
   * @return a list with all remaining objects of this iteration
   */
  @Override
  public List<E> asList() {
    return FluentIterable.copyToList(iterable);
  }

  @Override
  public Iterator<E> iterator() {
    return new DecoratingIterator<E>(iterable.iterator()) {
      @Override
      public E computeNext() {
        return fromIterator.hasNext() ? fromIterator.next() : null;
      }
    };
  }

  /**
   * @return a FluentIterable from a given iterable. Calls the LazyFluentIterable constructor.
   */
  public static final <E> FluentIterable<E> from(Iterable<E> iterable) {
    return new LazyFluentIterable<>(iterable);
  }

}
