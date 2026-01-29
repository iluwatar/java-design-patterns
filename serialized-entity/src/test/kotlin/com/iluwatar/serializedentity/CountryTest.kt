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
package com.iluwatar.serializedentity

// ABOUTME: Tests for the Country data class covering property access, mutation, and serialization.
// ABOUTME: Verifies that Country objects can be serialized to bytes and deserialized back correctly.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.file.Files
import java.nio.file.Paths
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

class CountryTest {

    @Test
    fun testGetMethod() {
        val china = Country(86, "China", "Asia", "Chinese")

        assertEquals(86, china.code)
        assertEquals("China", china.name)
        assertEquals("Asia", china.continents)
        assertEquals("Chinese", china.language)
    }

    @Test
    fun testSetMethod() {
        val country = Country(86, "China", "Asia", "Chinese")

        country.code = 971
        country.name = "UAE"
        country.continents = "West-Asia"
        country.language = "Arabic"

        assertEquals(971, country.code)
        assertEquals("UAE", country.name)
        assertEquals("West-Asia", country.continents)
        assertEquals("Arabic", country.language)
    }

    @Test
    fun testSerializable() {
        // Serializing Country
        try {
            val country = Country(86, "China", "Asia", "Chinese")
            val objectOutputStream = ObjectOutputStream(FileOutputStream("output.txt"))
            objectOutputStream.writeObject(country)
            objectOutputStream.close()
        } catch (e: IOException) {
            logger.error(e) { "Error occurred" }
        }

        // De-serialize Country
        try {
            val objectInputStream = ObjectInputStream(FileInputStream("output.txt"))
            val country = objectInputStream.readObject() as Country
            objectInputStream.close()
            println(country)

            val china = Country(86, "China", "Asia", "Chinese")

            assertEquals(china, country)
        } catch (e: Exception) {
            logger.error(e) { "Error occurred" }
        }
        try {
            Files.deleteIfExists(Paths.get("output.txt"))
        } catch (e: IOException) {
            logger.error(e) { "Error occurred" }
        }
    }
}
