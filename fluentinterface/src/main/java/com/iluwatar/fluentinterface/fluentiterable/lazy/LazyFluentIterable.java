/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
 * @param <TYPE> the type of the objects the iteration is about
 */
public class LazyFluentIterable<TYPE> implements FluentIterable<TYPE> {

  private final Iterable<TYPE> iterable;

  /**
   * This constructor creates a new LazyFluentIterable. It wraps the given iterable.
   * 
   * @param iterable the iterable this FluentIterable works on.
   */
  protected LazyFluentIterable(Iterable<TYPE> iterable) {
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
  public FluentIterable<TYPE> filter(Predicate<? super TYPE> predicate) {
    return new LazyFluentIterable<TYPE>() {
      @Override
      public Iterator<TYPE> iterator() {
        return new DecoratingIterator<TYPE>(iterable.iterator()) {
          @Override
          public TYPE computeNext() {
            while (fromIterator.hasNext()) {
              TYPE candidate = fromIterator.next();
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
  public Optional<TYPE> first() {
    Iterator<TYPE> resultIterator = first(1).iterator();
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
  public FluentIterable<TYPE> first(int count) {
    return new LazyFluentIterable<TYPE>() {
      @Override
      public Iterator<TYPE> iterator() {
        return new DecoratingIterator<TYPE>(iterable.iterator()) {
          int currentIndex;

          @Override
          public TYPE computeNext() {
            if (currentIndex < count && fromIterator.hasNext()) {
              TYPE candidate = fromIterator.next();
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
  public Optional<TYPE> last() {
    Iterator<TYPE> resultIterator = last(1).iterator();
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
  public FluentIterable<TYPE> last(int count) {
    return new LazyFluentIterable<TYPE>() {
      @Override
      public Iterator<TYPE> iterator() {
        return new DecoratingIterator<TYPE>(iterable.iterator()) {
          private int stopIndex;
          private int totalElementsCount;
          private List<TYPE> list;
          private int currentIndex;

          @Override
          public TYPE computeNext() {
            initialize();

            TYPE candidate = null;
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
              Iterator<TYPE> newIterator = iterable.iterator();
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
   * Transforms this FluentIterable into a new one containing objects of the type NEW_TYPE.
   * 
   * @param function a function that transforms an instance of TYPE into an instance of NEW_TYPE
   * @param <NEW_TYPE> the target type of the transformation
   * @return a new FluentIterable of the new type
   */
  @Override
  public <NEW_TYPE> FluentIterable<NEW_TYPE> map(Function<? super TYPE, NEW_TYPE> function) {
    return new LazyFluentIterable<NEW_TYPE>() {
      @Override
      public Iterator<NEW_TYPE> iterator() {
        return new DecoratingIterator<NEW_TYPE>(null) {
          Iterator<TYPE> oldTypeIterator = iterable.iterator();

          @Override
          public NEW_TYPE computeNext() {
            if (oldTypeIterator.hasNext()) {
              TYPE candidate = oldTypeIterator.next();
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
  public List<TYPE> asList() {
    return FluentIterable.copyToList(iterable);
  }

  @Override
  public Iterator<TYPE> iterator() {
    return new DecoratingIterator<TYPE>(iterable.iterator()) {
      @Override
      public TYPE computeNext() {
        return fromIterator.hasNext() ? fromIterator.next() : null;
      }
    };
  }

  /**
   * @return a FluentIterable from a given iterable. Calls the LazyFluentIterable constructor.
   */
  public static final <TYPE> FluentIterable<TYPE> from(Iterable<TYPE> iterable) {
    return new LazyFluentIterable<>(iterable);
  }

}
