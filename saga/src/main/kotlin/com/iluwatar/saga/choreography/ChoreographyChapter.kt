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

// ABOUTME: Interface defining the contract for choreography-based saga chapters.
// ABOUTME: Each chapter can execute, process, and rollback operations on a saga.
package com.iluwatar.saga.choreography

/**
 * ChoreographyChapter is an interface representing a contract for an external service. In that
 * case, a service needs to make a decision what to do further hence the server needs to get all
 * context representing [Saga]
 */
interface ChoreographyChapter {

    /**
     * In that case, every method is responsible to make a decision on what to do then.
     *
     * @param saga incoming saga
     * @return saga result
     */
    fun execute(saga: Saga): Saga

    /**
     * Get name method.
     *
     * @return service name.
     */
    val name: String

    /**
     * The operation executed in general case.
     *
     * @param saga incoming saga
     * @return result [Saga]
     */
    fun process(saga: Saga): Saga

    /**
     * The operation executed in rollback case.
     *
     * @param saga incoming saga
     * @return result [Saga]
     */
    fun rollback(saga: Saga): Saga
}
