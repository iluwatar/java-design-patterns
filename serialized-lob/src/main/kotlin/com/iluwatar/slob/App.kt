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

// ABOUTME: Main application entry point for the Serialized LOB pattern demonstration.
// ABOUTME: Demonstrates BLOB and CLOB serialization of object graphs to/from a database.
package com.iluwatar.slob

import com.iluwatar.slob.lob.Animal
import com.iluwatar.slob.lob.Forest
import com.iluwatar.slob.lob.Plant
import com.iluwatar.slob.serializers.BlobSerializer
import com.iluwatar.slob.serializers.ClobSerializer
import com.iluwatar.slob.serializers.LobSerializer
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

const val CLOB = "CLOB"

/**
 * Main entry point to program.
 *
 * In the SLOB pattern, the object graph is serialized into a single large object (a BLOB or
 * CLOB, for Binary Large Object or Character Large Object, respectively) and stored in the
 * database. When the object graph needs to be retrieved, it is read from the database and
 * deserialized back into the original object graph.
 *
 * A Forest is created using [createForest] with Animals and Plants along with their
 * respective relationships.
 *
 * Creates a [LobSerializer] using the method [createLobSerializer].
 *
 * Once created the serializer is passed to the [executeSerializer] which handles the
 * serialization, deserialization and persisting and loading from DB.
 *
 * @param args if first arg is CLOB then ClobSerializer is used else BlobSerializer is used.
 */
fun main(args: Array<String>) {
    val forest = createForest()
    val serializer = createLobSerializer(args)
    executeSerializer(forest, serializer)
}

/**
 * Creates a [LobSerializer] on the basis of input args.
 *
 * If input args are not empty and the value equals [CLOB] then a [ClobSerializer]
 * is created else a [BlobSerializer] is created.
 *
 * @param args if first arg is [CLOB] then ClobSerializer is instantiated else
 *     BlobSerializer is instantiated.
 */
private fun createLobSerializer(args: Array<String>): LobSerializer {
    return if (args.isNotEmpty() && args[0] == CLOB) {
        ClobSerializer()
    } else {
        BlobSerializer()
    }
}

/**
 * Creates a Forest with [Animal] and [Plant] along with their respective relationships.
 *
 * The method creates a [Forest] with 2 Plants Grass and Oak of type Herb and tree
 * respectively.
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
 * Serialize the input object using the input serializer and persist to DB. After this it loads
 * the same object back from DB and deserializes using the same serializer.
 *
 * @param forest Object to Serialize and Persist
 * @param lobSerializer Serializer to Serialize and Deserialize Object
 */
private fun executeSerializer(forest: Forest, lobSerializer: LobSerializer) {
    lobSerializer.use { serializer ->
        val serialized = serializer.serialize(forest)
        val id = serializer.persistToDb(1, forest.name, serialized)

        val fromDb = serializer.loadFromDb(id, Forest::class.simpleName!!)
        val forestFromDb = serializer.deSerialize(fromDb)

        logger.info { forestFromDb.toString() }
    }
}
