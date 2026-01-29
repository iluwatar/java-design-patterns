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

// ABOUTME: Utility object for JSON serialization and deserialization using Jackson ObjectMapper.
// ABOUTME: Provides functions to convert objects to JSON strings and JSON strings to objects/lists.
package com.iluwatar.dynamicproxy.tinyrestclient

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.IOException

private val logger = KotlinLogging.logger {}

object JsonUtil {

    private val objectMapper = ObjectMapper()

    /**
     * Convert an object to a JSON string representation.
     *
     * @param obj Object to convert.
     * @return JSON string, or null if conversion fails.
     */
    fun <T> objectToJson(obj: T): String? {
        return try {
            objectMapper.writeValueAsString(obj)
        } catch (e: JsonProcessingException) {
            logger.error(e) { "Cannot convert the object $obj to Json." }
            null
        }
    }

    /**
     * Convert a JSON string to an object of a class.
     *
     * @param json JSON string to convert.
     * @param clazz Object's class.
     * @return Object, or null if conversion fails.
     */
    fun <T> jsonToObject(json: String, clazz: Class<T>): T? {
        return try {
            objectMapper.readValue(json, clazz)
        } catch (e: IOException) {
            logger.error(e) { "Cannot convert the Json $json to class ${clazz.name}." }
            null
        }
    }

    /**
     * Convert a JSON string to a List of objects of a class.
     *
     * @param json JSON string to convert.
     * @param clazz Object's class.
     * @return List of objects, or empty list if conversion fails.
     */
    fun <T> jsonToList(json: String, clazz: Class<T>): List<T> {
        return try {
            val listType = objectMapper.typeFactory.constructCollectionType(ArrayList::class.java, clazz)
            objectMapper.reader().forType(listType).readValue(json)
        } catch (e: JsonProcessingException) {
            logger.error(e) { "Cannot convert the Json $json to List of ${clazz.name}." }
            emptyList()
        }
    }
}
