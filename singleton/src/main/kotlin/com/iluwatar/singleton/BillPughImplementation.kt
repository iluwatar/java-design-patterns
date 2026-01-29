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

// ABOUTME: Bill Pugh singleton implementation using a nested holder object for lazy initialization.
// ABOUTME: Leverages JVM class-loading guarantees for thread-safe, on-demand instance creation.

/**
 * Bill Pugh Singleton Implementation.
 *
 * This implementation of the singleton design pattern takes advantage of the Java memory model's
 * guarantees about class initialization. Each class is initialized only once, when it is first
 * used. If the class hasn't been used yet, it won't be loaded into memory, and no memory will be
 * allocated for a static instance. This makes the singleton instance lazy-loaded and thread-safe.
 */
class BillPughImplementation private constructor() {

    init {
        // Protect against instantiation via reflection
        if (created) {
            throw UnsupportedOperationException("Do not instantiate a singleton class via reflection")
        }
        created = true
    }

    /**
     * The InstanceHolder is a nested object, and it holds the Singleton instance. It is not
     * loaded into memory until the getInstance() method is called.
     */
    private object InstanceHolder {
        /** Singleton instance of the class. */
        val instance = BillPughImplementation()
    }

    companion object {
        @Volatile
        private var created = false

        /**
         * Public accessor for the singleton instance.
         *
         * When this method is called, the InstanceHolder is loaded into memory and creates the
         * Singleton instance. This method provides a global access point for the singleton instance.
         *
         * @return an instance of the class.
         */
        // global access point
        @JvmStatic
        fun getInstance(): BillPughImplementation = InstanceHolder.instance
    }
}
