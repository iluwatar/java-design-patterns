package com.iluwatar.trampoline;

import java.util.stream.Stream;

/**
 * <p>Trampoline pattern allows to define recursive algorithms by iterative loop </p>
 * <p>When get is called on the returned Trampoline, internally it will iterate calling ‘jump’
 * on the returned Trampoline as long as the concrete instance returned is {@link #more(Trampoline)},
 * stopping once the returned instance is {@link #done(Object)}.</p>
 * <p>Essential we convert looping via recursion into iteration,
 * the key enabling mechanism is the fact that {@link #more(Trampoline)} is a lazy operation.</p>
 *
 * @param <T> is  type for returning result.
 */
public interface Trampoline<T> {
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
