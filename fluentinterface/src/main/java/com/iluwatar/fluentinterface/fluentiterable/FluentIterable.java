package com.iluwatar.fluentinterface.fluentiterable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The FluentIterable is a more convenient implementation of the common iterable interface based
 * on the fluent interface design pattern.
 * This implementation demonstrates a possible way to implement this functionality, but
 * doesn't aim to be complete. It was inspired by Guava's com.google.common.collect.FluentIterable.
 * @param <TYPE> is the class of objects the iterable contains
 */
public class FluentIterable<TYPE> implements Iterable<TYPE> {

    private final Iterable<TYPE> iterable;

    /**
     * This constructor creates a copy of a given iterable's contents.
     * @param iterable the iterable this interface copies to work on.
     */
    protected FluentIterable(Iterable<TYPE> iterable) {
        ArrayList<TYPE> copy = new ArrayList<>();
        Iterator<TYPE> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            copy.add(iterator.next());
        }
        this.iterable = copy;
    }

    /**
     * Iterates over all elements of this iterator and filters them.
     * @param predicate the condition to test with for the filtering. If the test
     *                  is negative, the tested object is removed by the iterator.
     * @return the same FluentIterable with a filtered collection
     */
    public final FluentIterable<TYPE> filter(Predicate<? super TYPE> predicate) {
        Iterator<TYPE> iterator = iterator();
        while (iterator.hasNext()) {
            TYPE nextElement = iterator.next();
            if(!predicate.test(nextElement)) {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * Uses the Iterable interface's forEach method to apply a given function
     * for each object of the iterator.
     * @param action the action for each object
     * @return the same FluentIterable with an untouched collection
     */
    public final FluentIterable<TYPE> forEachDo(Consumer<? super TYPE> action) {
        iterable.forEach(action);
        return this;
    }

    /**
     * Can be used to collect objects from the iteration.
     * @return an option of the first object of the iteration
     */
    public final Optional<TYPE> first() {
        List<TYPE> list = first(1).asList();
        if(list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    /**
     * Can be used to collect objects from the iteration.
     * @param count defines the number of objects to return
     * @return the same FluentIterable with a collection decimated to a maximum of 'count' first objects.
     */
    public final FluentIterable<TYPE> first(int count) {
        Iterator<TYPE> iterator = iterator();
        int currentCount = 0;
        while (iterator.hasNext()) {
            iterator.next();
            if(currentCount >= count) {
                iterator.remove();
            }
            currentCount++;
        }
        return this;
    }

    /**
     * Can be used to collect objects from the iteration.
     * @return an option of the last object of the iteration
     */
    public final Optional<TYPE> last() {
        List<TYPE> list = last(1).asList();
        if(list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    /**
     * Can be used to collect objects from the iteration.
     * @param count defines the number of objects to return
     * @return the same FluentIterable with a collection decimated to a maximum of 'count' last objects
     */
    public final FluentIterable<TYPE> last(int count) {
        int remainingElementsCount = getRemainingElementsCount();
        Iterator<TYPE> iterator = iterator();
        int currentIndex = 0;
        while (iterator.hasNext()) {
            iterator.next();
            if(currentIndex < remainingElementsCount - count) {
                iterator.remove();
            }
            currentIndex++;
        }

        return this;
    }

    /**
     * Transforms this FluentIterable into a new one containing objects of the type NEW_TYPE.
     * @param function a function that transforms an instance of TYPE into an instance of NEW_TYPE
     * @param <NEW_TYPE> the target type of the transformation
     * @return a new FluentIterable of the new type
     */
    public final <NEW_TYPE> FluentIterable<NEW_TYPE> map(Function<? super TYPE, NEW_TYPE> function) {
        List<NEW_TYPE> temporaryList = new ArrayList();
        Iterator<TYPE> iterator = iterator();
        while (iterator.hasNext()) {
            temporaryList.add(function.apply(iterator.next()));
        }
        return from(temporaryList);
    }

    /**
     * Collects all remaining objects of this iteration into a list.
     * @return a list with all remaining objects of this iteration
     */
    public List<TYPE> asList() {
        return toList(iterable.iterator());
    }

    /**
     * @return a FluentIterable from a given iterable. Calls the FluentIterable constructor.
     */
    public static final <TYPE> FluentIterable<TYPE> from(Iterable<TYPE> iterable) {
        return new FluentIterable<>(iterable);
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
     * @return the count of remaining objects in the current iteration
     */
    public final int getRemainingElementsCount() {
        int counter = 0;
        Iterator<TYPE> iterator = iterator();
        while(iterator.hasNext()) {
            iterator.next();
            counter++;
        }
        return counter;
    }

    /**
     * Collects the remaining objects of the given iterators iteration into an List.
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
