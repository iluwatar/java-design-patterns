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
package com.iluwatar.filterer.threat

// ABOUTME: Data class implementing ProbableThreat, adding a probability to the base threat.
// ABOUTME: Delegates core threat properties via composition with SimpleThreat.

/** Represents a threat with a probability of occurrence. */
data class SimpleProbableThreat(
    private val name: String,
    private val id: Int,
    private val threatType: ThreatType,
    private val probability: Double
) : ProbableThreat {

    private val delegate = SimpleThreat(threatType, id, name)

    override fun name(): String = delegate.name()

    override fun id(): Int = delegate.id()

    override fun type(): ThreatType = delegate.type()

    override fun probability(): Double = probability

    override fun toString(): String =
        "SimpleProbableThreat(probability=$probability) ${delegate.toString()}"
}
