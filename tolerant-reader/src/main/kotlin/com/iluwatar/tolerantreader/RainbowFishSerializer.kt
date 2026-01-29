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
package com.iluwatar.tolerantreader

// ABOUTME: Provides serialization and deserialization for RainbowFish objects using the Tolerant Reader pattern.
// ABOUTME: Serializes maps instead of objects directly, so the reader does not break when the schema evolves.

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * RainbowFishSerializer provides methods for reading and writing [RainbowFish] objects to
 * file. Tolerant Reader pattern is implemented here by serializing maps instead of [RainbowFish]
 * objects. This way the reader does not break even though new properties are added to the schema.
 */
object RainbowFishSerializer {

    const val LENGTH_METERS = "lengthMeters"
    const val WEIGHT_TONS = "weightTons"

    /** Write V1 RainbowFish to file. */
    @JvmStatic
    fun writeV1(rainbowFish: RainbowFish, filename: String) {
        val map = mapOf(
            "name" to rainbowFish.name,
            "age" to "%d".format(rainbowFish.age),
            LENGTH_METERS to "%d".format(rainbowFish.lengthMeters),
            WEIGHT_TONS to "%d".format(rainbowFish.weightTons)
        )

        FileOutputStream(filename).use { fileOut ->
            ObjectOutputStream(fileOut).use { objOut ->
                objOut.writeObject(map)
            }
        }
    }

    /** Write V2 RainbowFish to file. */
    @JvmStatic
    fun writeV2(rainbowFish: RainbowFishV2, filename: String) {
        val map = mapOf(
            "name" to rainbowFish.name,
            "age" to "%d".format(rainbowFish.age),
            LENGTH_METERS to "%d".format(rainbowFish.lengthMeters),
            WEIGHT_TONS to "%d".format(rainbowFish.weightTons),
            "angry" to rainbowFish.angry.toString(),
            "hungry" to rainbowFish.hungry.toString(),
            "sleeping" to rainbowFish.sleeping.toString()
        )

        FileOutputStream(filename).use { fileOut ->
            ObjectOutputStream(fileOut).use { objOut ->
                objOut.writeObject(map)
            }
        }
    }

    /** Read V1 RainbowFish from file. */
    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun readV1(filename: String): RainbowFish {
        val map: Map<String, String>

        FileInputStream(filename).use { fileIn ->
            ObjectInputStream(fileIn).use { objIn ->
                map = objIn.readObject() as Map<String, String>
            }
        }

        return RainbowFish(
            map["name"]!!,
            map["age"]!!.toInt(),
            map[LENGTH_METERS]!!.toInt(),
            map[WEIGHT_TONS]!!.toInt()
        )
    }
}
