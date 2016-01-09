package com.iluwatar.fluentinterface.fluentiterable.simple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.iluwatar.fluentinterface.fluentiterable.FluentIterable;

/**
 * This is a simple implementation of the FluentIterable interface. It evaluates all chained
 * operations eagerly. This implementation would be costly to be utilized in real applications.
 * 
 * @param <TYPE> the type of the objects the iteration is about
 */
public class SimpleFluentIterable<TYPE> implements FluentIterable<TYPE> {

  private final Iterable<TYPE> iterable;

  /**
   * This constructor creates a copy of a given iterable's contents.
   * 
   * @param iterable the iterable this interface copies to work on.
   */
  protected SimpleFluentIterable(Iterable<TYPE> iterable) {
    this.iterable = iterable;
  }

  /**
   * Filters the contents of Iterable using the given predicate, leaving only the ones which satisfy
   * the predicate.
   * 
   * @param predicate the condition to test with for the filtering. If the test is negative, the
   *        tested object is removed by the iterator.
   * @return the same FluentIterable with a filtered collection
   */
  @Override
  public final FluentIterable<TYPE> filter(Predicate<? super TYPE> predicate) {
    Iterator<TYPE> iterator = iterator();
    while (iterator.hasNext()) {
      TYPE nextElement = iterator.next();
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
  public final Optional<TYPE> first() {
    Iterator<TYPE> resultIterator = first(1).iterator();
    return resultIterator.hasNext() ? Optional.of(resultIterator.next()) : Optional.empty();
  }

  /**
   * Can be used to collect objects from the Iterable. Is a terminating operation.
   * 
   * @param count defines the number of objects to return
   * @return the same FluentIterable with a collection decimated to a maximum of 'count' first
   *         objects.
   */
  @Override
  public final FluentIterable<TYPE> first(int count) {
    Iterator<TYPE> iterator = iterator();
    int currentCount = 0;
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
  public final Optional<TYPE> last() {
    List<TYPE> list = last(1).asList();
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
   *         objects
   */
  @Override
  public final FluentIterable<TYPE> last(int count) {
    int remainingElementsCount = getRemainingElementsCount();
    Iterator<TYPE> iterator = iterator();
    int currentIndex = 0;
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
   * Transforms this FluentIterable into a new one containing objects of the type NEW_TYPE.
   * 
   * @param function a function that transforms an instance of TYPE into an instance of NEW_TYPE
   * @param <NEW_TYPE> the target type of the transformation
   * @return a new FluentIterable of the new type
   */
  @Override
  public final <NEW_TYPE> FluentIterable<NEW_TYPE> map(Function<? super TYPE, NEW_TYPE> function) {
    List<NEW_TYPE> temporaryList = new ArrayList<>();
    Iterator<TYPE> iterator = iterator();
    while (iterator.hasNext()) {
      temporaryList.add(function.apply(iterator.next()));
    }
    return from(temporaryList);
  }

  /**
   * Collects all remaining objects of this Iterable into a list.
   * 
   * @return a list with all remaining objects of this Iterable
   */
  @Override
  public List<TYPE> asList() {
    return toList(iterable.iterator());
  }

  /**
   * @return a FluentIterable from a given iterable. Calls the SimpleFluentIterable constructor.
   */
  public static final <TYPE> FluentIterable<TYPE> from(Iterable<TYPE> iterable) {
    return new SimpleFluentIterable<>(iterable);
  }

  public static final <TYPE> FluentIterable<TYPE> fromCopyOf(Iterable<TYPE> iterable) {
    List<TYPE> copy = FluentIterable.copyToList(iterable);
    return new SimpleFluentIterable<>(copy);
  }

  @Override
  public Iterator<TYPE> iterator() {
    return iterable.iterator();
  }

  @Override
  public void forEach(Consumer<? super TYPE> action) {
    iterable.forEach(action);
  }


  @Override
  public Spliterator<TYPE> spliterator() {
    return iterable.spliterator();
  }

  /**
   * @return the count of remaining objects of the current Iterable
   */
  public final int getRemainingElementsCount() {
    int counter = 0;
    Iterator<TYPE> iterator = iterator();
    while (iterator.hasNext()) {
      iterator.next();
      counter++;
    }
    return counter;
  }

  /**
   * Collects the remaining objects of the given iterator into a List.
   * 
   * @return a new List with the remaining objects.
   */
  public static <TYPE> List<TYPE> toList(Iterator<TYPE> iterator) {
    List<TYPE> copy = new ArrayList<>();
    while (iterator.hasNext()) {
      copy.add(iterator.next());
    }
    return copy;
  }
}
