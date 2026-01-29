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

// ABOUTME: Defines the Document interface for the Abstract Document pattern.
// ABOUTME: Provides methods for storing, retrieving, and navigating hierarchical document properties.
package com.iluwatar.abstractdocument

import java.util.stream.Stream

/**
 * Document interface.
 */
interface Document {
    /**
     * Puts the value related to the key.
     *
     * @param key element key
     * @param value element value
     */
    fun put(
        key: String,
        value: Any?,
    )

    /**
     * Gets the value for the key.
     *
     * @param key element key
     * @return value or null
     */
    fun get(key: String): Any?

    /**
     * Gets the stream of child documents.
     *
     * @param key element key
     * @param constructor constructor of child class
     * @return child documents
     */
    fun <T> children(
        key: String,
        constructor: (Map<String, Any>) -> T,
    ): Stream<T>
}