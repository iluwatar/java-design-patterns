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
package com.iluwatar.monad

// ABOUTME: Monad-style validator that chains validation steps on an object.
// ABOUTME: Collects all validation failures and throws them together on get().

/**
 * Class representing Monad design pattern. Monad is a way of chaining operations on the given
 * object together step by step. In Validator each step results in either success or failure
 * indicator, giving a way of receiving each of them easily and finally getting validated object or
 * list of exceptions.
 *
 * @param T Placeholder for an object.
 */
class Validator<T> private constructor(
    /** Object that is validated. */
    private val obj: T,
) {
    /** List of exceptions thrown during validation. */
    private val exceptions: MutableList<Throwable> = mutableListOf()

    /**
     * Checks if the validation is successful.
     *
     * @param validation one argument boolean-valued function that represents one step of validation.
     *     Adds exception to main validation exception list when single step validation ends with
     *     failure.
     * @param message error message when object is invalid
     * @return this
     */
    fun validate(validation: (T) -> Boolean, message: String): Validator<T> {
        if (!validation(obj)) {
            exceptions.add(IllegalStateException(message))
        }
        return this
    }

    /**
     * Extension for the [validate] method, dedicated for objects that need to be projected before
     * requested validation.
     *
     * @param projection function that gets an object and returns projection representing element to
     *     be validated.
     * @param validation see [validate]
     * @param message see [validate]
     * @return this
     */
    fun <U> validate(projection: (T) -> U, validation: (U) -> Boolean, message: String): Validator<T> =
        validate({ validation(projection(it)) }, message)

    /**
     * Receives validated object or throws exception when invalid.
     *
     * @return object that was validated
     * @throws IllegalStateException when any validation step results with failure
     */
    fun get(): T {
        if (exceptions.isEmpty()) {
            return obj
        }
        val e = IllegalStateException()
        exceptions.forEach { e.addSuppressed(it) }
        throw e
    }

    companion object {
        /**
         * Creates validator against given object.
         *
         * @param t object to be validated
         * @return new instance of a validator
         */
        fun <T> of(t: T & Any): Validator<T> = Validator(t)
    }
}
