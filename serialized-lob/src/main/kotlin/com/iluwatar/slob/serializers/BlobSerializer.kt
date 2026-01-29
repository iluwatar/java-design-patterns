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

// ABOUTME: BLOB serializer implementation for binary serialization of object graphs.
// ABOUTME: Uses Java Object streams to serialize/deserialize Forest objects to binary format.
package com.iluwatar.slob.serializers

import com.iluwatar.slob.lob.Forest
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * Creates a Serializer that uses Binary serialization and deserialization of objects graph to and
 * from their Binary Representation.
 */
class BlobSerializer : LobSerializer(TYPE_OF_DATA_FOR_DB) {

    companion object {
        const val TYPE_OF_DATA_FOR_DB = "BINARY"
    }

    /**
     * Serializes the input object graph to its Binary Representation using Object Stream.
     *
     * @param toSerialize Object which is to be serialized
     * @return Serialized object
     */
    override fun serialize(toSerialize: Forest): Any {
        val baos = ByteArrayOutputStream()
        ObjectOutputStream(baos).use { oos ->
            oos.writeObject(toSerialize)
        }
        return ByteArrayInputStream(baos.toByteArray())
    }

    /**
     * Deserializes the input Byte Array Stream using Object Stream and return its Object Graph
     * Representation.
     *
     * @param toDeserialize Input Object to De-serialize
     * @return Deserialized Object
     */
    override fun deSerialize(toDeserialize: Any?): Forest {
        val bis = toDeserialize as InputStream
        ObjectInputStream(bis).use { ois ->
            return ois.readObject() as Forest
        }
    }
}
