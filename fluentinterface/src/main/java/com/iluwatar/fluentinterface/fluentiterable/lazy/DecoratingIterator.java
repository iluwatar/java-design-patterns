package com.iluwatar.fluentinterface.fluentiterable.lazy;

import java.util.Iterator;

/**
 * This class is used to realize LazyFluentIterables. It decorates
 * a given iterator.
 * @param <TYPE>
 */
public abstract class DecoratingIterator<TYPE> implements Iterator<TYPE> {

    protected final Iterator<TYPE> fromIterator;

    private TYPE next = null;

    /**
     * Creates an iterator that decorates the given iterator.
     * @param fromIterator
     */
    public DecoratingIterator(Iterator<TYPE> fromIterator) {
        this.fromIterator = fromIterator;
    }

    /**
     * Precomputes and caches the next element of the iteration.
     * @return true if a next element is available
     */
    @Override
    public final boolean hasNext() {
        next = computeNext();
        return next != null;
    }

    /**
     * Returns the next element of the iteration. This implementation caches it.
     * If no next element is cached, it is computed.
     * @return the next element obf the iteration
     */
    @Override
    public final TYPE next() {
        TYPE result = next;
        next = null;
        result = (result == null ? fromIterator.next() : result);
        return result;
    }

    /**
     * Computes the next object of the iteration. Can be implemented to
     * realize custom behaviour for an iteration process.
     * @return
     */
    public abstract TYPE computeNext();
}
