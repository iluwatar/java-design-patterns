/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.trampoline;

import java.util.stream.Stream;

/**
 * Trampoline pattern allows to define recursive algorithms by iterative loop.
 *
 * <p>When get is called on the returned Trampoline, internally it will iterate calling ‘jump’
 * on the returned Trampoline as long as the concrete instance returned is {@link
 * #more(Trampoline)}, stopping once the returned instance is {@link #done(Object)}.
 *
 * <p>Essential we convert looping via recursion into iteration,
 * the key enabling mechanism is the fact that {@link #more(Trampoline)} is a lazy operation.
 *
 * @param <T> is  type for returning result.
 */
public interface Trampoline<T> {
  T get();


  /**
   * Jump to next stage.
   *
   * @return next stage
   */
  default Trampoline<T> jump() {
    return this;
  }


  default T result() {
    return get();
  }

  /**
   * Checks if complete.
   *
   * @return true if complete
   */
  default boolean complete() {
    return true;
  }

  /**
   * Created a completed Trampoline.
   *
   * @param result Completed result
   * @return Completed Trampoline
   */
  static <T> Trampoline<T> done(final T result) {
    return () -> result;
  }


  /**
   * Create a Trampoline that has more work to do.
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
            .map(Trampoline::result)
            .get();
      }
    };
  }
}
