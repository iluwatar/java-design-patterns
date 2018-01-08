package com.iluwatar.trampoline;

import java.util.stream.Stream;

/**When get is called on the returned Trampoline, internally it will iterate calling ‘jump’
 on the returned Trampoline as long as the concrete instance returned is More,
 stopping once the returned instance is Done. Essential we convert looping via recursion into iteration,
 the key enabling mechanism is the fact that Trampoline.more is a lazy operation.
 Trampoline in cyclops-react extends java.util.Supplier. Calling Trampoline.more we are basically creating
 a Supplier that defers the actual recursive call, and having defered the call we can move it outside of the recursive loop.
 This means we can define algorithms recursively in Java but execute them iteratively.*/

public interface Trampoline<T>  {
    T get();


    /**
     * @return next stage
     */
    default Trampoline<T> jump() {
        return this;
    }


    default T result() {
        return get();
    }

    /**
     * @return true if complete
     *
     */
    default boolean complete() {
        return true;
    }

    /**
     * Created a completed Trampoline
     *
     * @param result Completed result
     * @return Completed Trampoline
     */
    static <T> Trampoline<T> done(final T result) {
        return () -> result;
    }


    /**
     * Create a Trampoline that has more work to do
     *
     * @param trampoline Next stage in Trampoline
     * @return Trampoline with more work
     */
    static <T> Trampoline<T> more(final Trampoline<Trampoline<T>> trampoline) {
        return new Trampoline<T>() {
            @Override
            public boolean complete() {
                return false;
            }

            @Override
            public Trampoline<T> jump() {
                return trampoline.result();
            }

            @Override
            public T get() {
                return trampoline(this);
            }

            T trampoline(final Trampoline<T> trampoline) {

                return Stream.iterate(trampoline, Trampoline::jump)
                        .filter(Trampoline::complete)
                        .findFirst()
                        .get()
                        .result();

            }
        };
    }


}
