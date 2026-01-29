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

// ABOUTME: Double-checked locking singleton using volatile field and synchronized block.
// ABOUTME: Demonstrates the double-checked locking pattern for thread-safe lazy initialization.

/**
 * Double check locking.
 *
 * http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
 *
 * Broken under Java 1.4.
 *
 * This implementation is somewhat faster than [ThreadSafeLazyLoadedIvoryTower] since it doesn't
 * synchronize the whole access method but only the method internals on specific conditions.
 */
class ThreadSafeDoubleCheckLocking private constructor() {

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
        private var instance: ThreadSafeDoubleCheckLocking? = null

        /**
         * Public accessor.
         *
         * @return an instance of the class.
         */
        @JvmStatic
        fun getInstance(): ThreadSafeDoubleCheckLocking {
            // local variable increases performance by 25 percent
            // Joshua Bloch "Effective Java, Second Edition", p. 283-284
            var result = instance
            // Check if singleton instance is initialized.
            // If it is initialized then we can return the instance.
            if (result == null) {
                // It is not initialized, but we cannot be sure because some other thread might have
                // initialized it in the meanwhile.
                // So to make sure we need to lock on an object to get mutual exclusion.
                synchronized(ThreadSafeDoubleCheckLocking::class.java) {
                    // Again assign the instance to local variable to check if it was initialized by
                    // some other thread while current thread was blocked to enter the locked zone.
                    // If it was initialized then we can return the previously created instance
                    // just like the previous null check.
                    result = instance
                    if (result == null) {
                        // The instance is still not initialized, so we can safely
                        // (no other thread can enter this zone)
                        // create an instance and make it our singleton instance.
                        result = ThreadSafeDoubleCheckLocking()
                        instance = result
                    }
                }
            }
            return result!!
        }
    }
}
