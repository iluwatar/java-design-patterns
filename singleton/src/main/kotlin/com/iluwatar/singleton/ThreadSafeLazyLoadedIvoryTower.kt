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
package com.iluwatar.singleton

// ABOUTME: Thread-safe lazy-loaded singleton using synchronized getInstance method.
// ABOUTME: Demonstrates the synchronized-method approach to lazy singleton initialization.

/**
 * Thread-safe Singleton class. The instance is lazily initialized and thus needs a synchronization
 * mechanism.
 *
 * In Kotlin, this approach uses a companion object with a @Volatile field and a synchronized
 * getInstance() method, mirroring the Java pattern where the entire accessor is synchronized.
 * The downside is that it is very slow to access since the whole access method is synchronized.
 */
class ThreadSafeLazyLoadedIvoryTower private constructor() {

    init {
        // Protect against instantiation via reflection
        if (created) {
            throw UnsupportedOperationException("Do not instantiate a singleton class via reflection")
        }
        created = true
    }

    companion object {
        @Volatile
        private var created = false
        /**
         * Singleton instance of the class, declared as @Volatile to ensure atomic access by
         * multiple threads.
         */
        @Volatile
        private var instance: ThreadSafeLazyLoadedIvoryTower? = null

        /**
         * The instance doesn't get created until the method is called for the first time.
         *
         * @return an instance of the class.
         */
        @JvmStatic
        @Synchronized
        fun getInstance(): ThreadSafeLazyLoadedIvoryTower {
            if (instance == null) {
                instance = ThreadSafeLazyLoadedIvoryTower()
            }
            return instance!!
        }
    }
}
