package com.iluwatar.fluentinterface.fluentiterable.lazy;

import com.iluwatar.fluentinterface.fluentiterable.FluentIterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This is a lazy implementation of the FluentIterable interface. It evaluates
 * all chained operations when a terminating operation is applied.
 * @param <TYPE> the type of the objects the iteration is about
 */
public class LazyFluentIterable<TYPE> implements FluentIterable<TYPE> {

    private final Iterable<TYPE> iterable;

    /**
     * This constructor creates a new LazyFluentIterable. It wraps the
     * given iterable.
     * @param iterable the iterable this FluentIterable works on.
     */
    protected LazyFluentIterable(Iterable<TYPE> iterable) {
        this.iterable = iterable;
    }

    /**
     * This constructor can be used to implement anonymous subclasses
     * of the LazyFluentIterable.
     */
    protected LazyFluentIterable() {
        iterable = this;
    }

    /**
     * Adds a filter operation to the operation chain and returns a new iterable.
     * @param predicate the condition to test with for the filtering. If the test
     *                  is negative, the tested object is removed by the iterator.
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
                        while(true) {
                            if(fromIterator.hasNext()) {
                                TYPE candidate = fromIterator.next();
                                if(!predicate.test(candidate)) {
                                    continue;
                                }
                                return candidate;
                            }

                            return null;
                        }
                    }
                };
            }
        };
    }

    /**
     * Uses the Iterable interface's forEach method to apply a given function
     * for each object of the iterator. Is a terminating operation.
     * @param action the action for each object
     */
    @Override
    public void forEachDo(Consumer<? super TYPE> action) {
        Iterator<TYPE> newIterator = iterable.iterator();
        while(newIterator.hasNext()) {
            action.accept(newIterator.next());
        }
    }

    /**
     * Can be used to collect objects from the iteration. Is a terminating operation.
     * @return an option of the first object of the iteration
     */
    @Override
    public Optional<TYPE> first() {
        Optional result = Optional.empty();
        List<TYPE> list = first(1).asList();
        if(!list.isEmpty()) {
            result = Optional.of(list.get(0));
        }

        return result;
    }

    /**
     * Can be used to collect objects from the iteration. Is a terminating operation.
     * @param count defines the number of objects to return
     * @return the same FluentIterable with a collection decimated to a maximum of 'count' first objects.
     */
    @Override
    public FluentIterable<TYPE> first(int count) {
        return new LazyFluentIterable<TYPE>() {
            @Override
            public Iterator<TYPE> iterator() {
                return new DecoratingIterator<TYPE>(iterable.iterator()) {
                    int currentIndex = 0;

                    @Override
                    public TYPE computeNext() {
                        if(currentIndex < count) {
                            if(fromIterator.hasNext()) {
                                TYPE candidate = fromIterator.next();
                                currentIndex++;
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
     * @return an option of the last object of the iteration
     */
    @Override
    public Optional<TYPE> last() {
        Optional result = Optional.empty();
        List<TYPE> list = last(1).asList();
        if(!list.isEmpty()) {
            result = Optional.of(list.get(0));
        }

        return result;
    }

    /**
     * Can be used to collect objects from the iteration. Is a terminating operation.
     * @param count defines the number of objects to return
     * @return the same FluentIterable with a collection decimated to a maximum of 'count' last objects
     */
    @Override
    public FluentIterable<TYPE> last(int count) {return new LazyFluentIterable<TYPE>() {
        @Override
        public Iterator<TYPE> iterator() {
            return new DecoratingIterator<TYPE>(iterable.iterator()) {
                int currentIndex = 0;

                @Override
                public TYPE computeNext() {
                    List<TYPE> list = new ArrayList<>();

                    Iterator<TYPE> newIterator = iterable.iterator();
                    while(newIterator.hasNext()) {
                        list.add(newIterator.next());
                    }

                    int totalElementsCount = list.size();
                    int stopIndex = totalElementsCount - count;

                    TYPE candidate = null;
                    while(currentIndex < stopIndex && fromIterator.hasNext()) {
                        currentIndex++;
                        fromIterator.next();
                    }
                    if(currentIndex >= stopIndex && fromIterator.hasNext()) {
                        candidate = fromIterator.next();
                    }
                    return candidate;
                }
            };
        }
    };
    }

    /**
     * Transforms this FluentIterable into a new one containing objects of the type NEW_TYPE.
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
                        while(true) {
                            if(oldTypeIterator.hasNext()) {
                                TYPE candidate = oldTypeIterator.next();
                                return function.apply(candidate);
                            }
                            return null;
                        }
                    }
                };
            }
        };
    }

    /**
     * Collects all remaining objects of this iteration into a list.
     * @return a list with all remaining objects of this iteration
     */
    @Override
    public List<TYPE> asList() {
        List<TYPE> copy = FluentIterable.copyToList(iterable);
        return copy;
    }

    @Override
    public Iterator<TYPE> iterator() {
        return new DecoratingIterator<TYPE>(iterable.iterator()) {
            @Override
            public TYPE computeNext() {
                return fromIterator.next();
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
