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

// ABOUTME: Test suite for the Serialized LOB pattern implementation.
// ABOUTME: Tests BLOB and CLOB serializers with round-trip database persistence.
package com.iluwatar.slob

import com.iluwatar.slob.lob.Animal
import com.iluwatar.slob.lob.Forest
import com.iluwatar.slob.lob.Plant
import com.iluwatar.slob.serializers.BlobSerializer
import com.iluwatar.slob.serializers.ClobSerializer
import com.iluwatar.slob.serializers.LobSerializer
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private val logger = KotlinLogging.logger {}

/**
 * SLOB Application test
 */
class AppTest {

    /**
     * Creates a Forest with Animals and Plants along with their respective relationships.
     *
     * The method creates a forest with 2 Plants Grass and Oak of type Herb and tree respectively.
     *
     * It also creates 3 animals Zebra and Buffalo which eat the plant grass. Lion consumes the
     * Zebra and the Buffalo.
     *
     * With the above animals and plants and their relationships a forest object is created which
     * represents the Object Graph.
     *
     * @return Forest Object
     */
    private fun createForest(): Forest {
        val grass = Plant("Grass", "Herb")
        val oak = Plant("Oak", "Tree")

        val zebra = Animal("Zebra", setOf(grass), emptySet())
        val buffalo = Animal("Buffalo", setOf(grass), emptySet())
        val lion = Animal("Lion", emptySet(), setOf(zebra, buffalo))

        return Forest("Amazon", setOf(lion, buffalo, zebra), setOf(grass, oak))
    }

    /**
     * Tests the [main] without passing any argument in the args to test the [ClobSerializer].
     */
    @Test
    fun shouldExecuteWithoutExceptionClob() {
        assertDoesNotThrow { main(arrayOf("CLOB")) }
    }

    /**
     * Tests the [main] without passing any argument in the args to test the [BlobSerializer].
     */
    @Test
    fun shouldExecuteWithoutExceptionBlob() {
        assertDoesNotThrow { main(arrayOf()) }
    }

    /**
     * Tests the serialization of the input object using the [ClobSerializer] and persists the
     * serialized object to DB, then load the object back from DB and deserializes it using the
     * provided [ClobSerializer].
     *
     * After loading the object back from DB the test matches the hash of the input object with the
     * hash of the object that was loaded from DB and deserialized.
     */
    @Test
    fun clobSerializerTest() {
        val forest = createForest()
        val serializer: LobSerializer = ClobSerializer()
        serializer.use {
            val serialized = it.serialize(forest)
            val id = it.persistToDb(1, forest.name, serialized)

            val fromDb = it.loadFromDb(id, Forest::class.simpleName!!)
            val forestFromDb = it.deSerialize(fromDb)

            assertEquals(
                forest.hashCode(),
                forestFromDb.hashCode(),
                "Hashes of objects after Serializing and Deserializing are the same"
            )
        }
    }

    /**
     * Tests the serialization of the input object using the [BlobSerializer] and persists the
     * serialized object to DB, then loads the object back from DB and deserializes it using the
     * [BlobSerializer].
     *
     * After loading the object back from DB the test matches the hash of the input object with the
     * hash of the object that was loaded from DB and deserialized.
     */
    @Test
    fun blobSerializerTest() {
        val forest = createForest()
        val serializer: LobSerializer = BlobSerializer()
        serializer.use {
            val serialized = it.serialize(forest)
            val id = it.persistToDb(1, forest.name, serialized)

            val fromDb = it.loadFromDb(id, Forest::class.simpleName!!)
            val forestFromDb = it.deSerialize(fromDb)

            assertEquals(
                forest.hashCode(),
                forestFromDb.hashCode(),
                "Hashes of objects after Serializing and Deserializing are the same"
            )
        }
    }
}
