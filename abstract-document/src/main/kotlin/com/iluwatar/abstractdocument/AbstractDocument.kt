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

// ABOUTME: Abstract implementation of the Document interface using a mutable property map.
// ABOUTME: Provides base functionality for property storage, retrieval, and child document navigation.
package com.iluwatar.abstractdocument

import java.util.stream.Stream

/**
 * Abstract implementation of Document interface.
 */
abstract class AbstractDocument internal constructor(
    properties: Map<String, Any>,
) : Document {
    private val documentProperties: MutableMap<String, Any> = properties.toMutableMap()

    init {
        requireNotNull(properties) { "properties map is required" }
    }

    override fun put(
        key: String,
        value: Any?,
    ) {
        if (value != null) {
            documentProperties[key] = value
        } else {
            documentProperties.remove(key)
        }
    }

    override fun get(key: String): Any? = documentProperties[key]

    @Suppress("UNCHECKED_CAST")
    override fun <T> children(
        key: String,
        constructor: (Map<String, Any>) -> T,
    ): Stream<T> =
        Stream
            .ofNullable(get(key))
            .filter { it != null }
            .map { it as List<Map<String, Any>> }
            .findAny()
            .stream()
            .flatMap { it.stream() }
            .map(constructor)

    override fun toString(): String = buildStringRepresentation()

    private fun buildStringRepresentation(): String {
        val builder = StringBuilder()
        builder.append(this::class.java.name).append("[")

        val numProperties = documentProperties.size
        var currentPropertyIndex = 0

        for ((key, value) in documentProperties) {
            builder
                .append("[")
                .append(key)
                .append(" : ")
                .append(value)
                .append("]")

            if (++currentPropertyIndex < numProperties) {
                builder.append(", ")
            }
        }

        builder.append("]")
        return builder.toString()
    }
}