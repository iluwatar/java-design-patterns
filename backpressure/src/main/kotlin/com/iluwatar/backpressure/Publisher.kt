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
package com.iluwatar.backpressure

// ABOUTME: Publisher that generates a data stream of integers with configurable delay.
// ABOUTME: Uses Project Reactor's Flux to emit values for backpressure demonstration.

import reactor.core.publisher.Flux
import java.time.Duration

/**
 * This class is the publisher that generates the data stream.
 */
object Publisher {

    /**
     * On message method will trigger when the subscribed event is published.
     *
     * @param start starting integer
     * @param count how many integers to emit
     * @param delay delay between each item in milliseconds
     * @return a flux stream of integers
     */
    @JvmStatic
    fun publish(start: Int, count: Int, delay: Int): Flux<Int> {
        return Flux.range(start, count)
            .delayElements(Duration.ofMillis(delay.toLong()))
            .log()
    }
}
