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

// ABOUTME: Entry point demonstrating the Monad (Validator) design pattern.
// ABOUTME: Chains multiple validation steps on a User object and logs the result.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Monad pattern defines a monad structure, that enables chaining operations in pipelines and
 * processing data step by step. Formally, monad consists of a type constructor M and two
 * operations:
 * - bind - that takes monadic object and a function from plain object to the monadic value and
 *   returns monadic value.
 * - return - that takes plain type object and returns this object wrapped in a monadic value.
 *
 * In the given example, the Monad pattern is represented as a [Validator] that takes an
 * instance of a plain object with [Validator.of] and validates it with
 * [Validator.validate] against given predicates.
 *
 * As a validation result [Validator.get] either returns valid object or throws
 * [IllegalStateException] with list of exceptions collected during validation.
 */
fun main() {
    val user = User("user", 24, Sex.FEMALE, "foobar.com")
    logger.info {
        Validator.of(user)
            .validate({ it.name != null }, "name is null")
            .validate({ it.name?.isNotEmpty() == true }, "name is empty")
            .validate({ it.email.contains("@").not() }, "email doesn't contains '@'")
            .validate({ it.age in 21..29 }, "age isn't between...")
            .get()
            .toString()
    }
}
