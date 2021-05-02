/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.monad;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Class representing Monad design pattern. Monad is a way of chaining operations on the given
 * object together step by step. In Validator each step results in either success or failure
 * indicator, giving a way of receiving each of them easily and finally getting validated object or
 * list of exceptions.
 *
 * @param <T> Placeholder for an object.
 */
public class Validator<T> {
  /**
   * Object that is validated.
   */
  private final T obj;

  /**
   * List of exception thrown during validation.
   */
  private final List<Throwable> exceptions = new ArrayList<>();

  /**
   * Creates a monadic value of given object.
   *
   * @param obj object to be validated
   */
  private Validator(T obj) {
    this.obj = obj;
  }

  /**
   * Creates validator against given object.
   *
   * @param t   object to be validated
   * @param <T> object's type
   * @return new instance of a validator
   */
  public static <T> Validator<T> of(T t) {
    return new Validator<>(Objects.requireNonNull(t));
  }

  /**
   * Checks if the validation is successful.
   *
   * @param validation one argument boolean-valued function that represents one step of validation.
   *                   Adds exception to main validation exception list when single step validation
   *                   ends with failure.
   * @param message    error message when object is invalid
   * @return this
   */
  public Validator<T> validate(Predicate<T> validation, String message) {
    if (!validation.test(obj)) {
      exceptions.add(new IllegalStateException(message));
    }
    return this;
  }

  /**
   * Extension for the {@link Validator#validate(Predicate, String)} method, dedicated for objects,
   * that need to be projected before requested validation.
   *
   * @param projection function that gets an objects, and returns projection representing element to
   *                   be validated.
   * @param validation see {@link Validator#validate(Predicate, String)}
   * @param message    see {@link Validator#validate(Predicate, String)}
   * @param <U>        see {@link Validator#validate(Predicate, String)}
   * @return this
   */
  public <U> Validator<T> validate(
      Function<T, U> projection,
      Predicate<U> validation,
      String message
  ) {
    return validate(projection.andThen(validation::test)::apply, message);
  }

  /**
   * Receives validated object or throws exception when invalid.
   *
   * @return object that was validated
   * @throws IllegalStateException when any validation step results with failure
   */
  public T get() throws IllegalStateException {
    if (exceptions.isEmpty()) {
      return obj;
    }
    var e = new IllegalStateException();
    exceptions.forEach(e::addSuppressed);
    throw e;
  }
}
