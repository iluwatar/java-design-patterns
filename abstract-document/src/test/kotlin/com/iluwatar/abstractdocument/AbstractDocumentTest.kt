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

// ABOUTME: Unit tests for AbstractDocument verifying put, get, children, and toString functionality.
// ABOUTME: Tests include nested documents, value updates, and null property handling.
package com.iluwatar.abstractdocument

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * AbstractDocument test class
 */
class AbstractDocumentTest {
    private class DocumentImplementation(
        properties: Map<String, Any>,
    ) : AbstractDocument(properties)

    private val document = DocumentImplementation(mutableMapOf())

    @Test
    fun shouldPutAndGetValue() {
        document.put(KEY, VALUE)
        assertEquals(VALUE, document.get(KEY))
    }

    @Test
    fun shouldRetrieveChildren() {
        val children = listOf(emptyMap<String, Any>(), emptyMap())

        document.put(KEY, children)

        val childrenStream = document.children(KEY) { DocumentImplementation(it) }
        assertNotNull(children)
        assertEquals(2, childrenStream.count())
    }

    @Test
    fun shouldRetrieveEmptyStreamForNonExistingChildren() {
        val children = document.children(KEY) { DocumentImplementation(it) }
        assertNotNull(children)
        assertEquals(0, children.count())
    }

    @Test
    fun shouldIncludePropsInToString() {
        val props = mapOf(KEY to VALUE as Any)
        val document = DocumentImplementation(props)
        assertTrue(document.toString().contains(KEY))
        assertTrue(document.toString().contains(VALUE))
    }

    @Test
    fun shouldHandleExceptionDuringConstruction() {
        assertThrows(NullPointerException::class.java) {
            @Suppress("UNCHECKED_CAST")
            DocumentImplementation(null as Map<String, Any>)
        }
    }

    @Test
    fun shouldPutAndGetNestedDocument() {
        val nestedDocument = DocumentImplementation(mutableMapOf())
        nestedDocument.put("nestedKey", "nestedValue")

        document.put("nested", nestedDocument)

        val retrievedNestedDocument = document.get("nested") as DocumentImplementation

        assertNotNull(retrievedNestedDocument)
        assertEquals("nestedValue", retrievedNestedDocument.get("nestedKey"))
    }

    @Test
    fun shouldUpdateExistingValue() {
        val key = "key"
        val originalValue = "originalValue"
        val updatedValue = "updatedValue"

        document.put(key, originalValue)

        assertEquals(originalValue, document.get(key))

        document.put(key, updatedValue)

        assertEquals(updatedValue, document.get(key))
    }

    companion object {
        private const val KEY = "key"
        private const val VALUE = "value"
    }
}