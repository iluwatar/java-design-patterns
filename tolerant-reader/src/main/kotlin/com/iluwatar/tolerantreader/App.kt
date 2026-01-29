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

// ABOUTME: Entry point demonstrating the Tolerant Reader integration pattern.
// ABOUTME: Shows how V1 reader handles both V1 and V2 serialized data without breaking.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Tolerant Reader is an integration pattern that helps to create robust communication systems. The
 * idea is to be as tolerant as possible when reading data from another service. This way, when the
 * communication schema changes, the readers must not break.
 *
 * In this example we use Java serialization to write representations of [RainbowFish]
 * objects to file. [RainbowFish] is the initial version which we can easily read and write
 * using [RainbowFishSerializer] methods. [RainbowFish] then evolves to [RainbowFishV2]
 * and we again write it to file with a method designed to do just that. However, the
 * reader client does not know about the new format and still reads with the method designed for V1
 * schema. Fortunately the reading method has been designed with the Tolerant Reader pattern and
 * does not break even though [RainbowFishV2] has new fields that are serialized.
 */
fun main() {
    // Write V1
    val fishV1 = RainbowFish("Zed", 10, 11, 12)
    logger.info {
        "fishV1 name=${fishV1.name} age=${fishV1.age} length=${fishV1.lengthMeters} weight=${fishV1.weightTons}"
    }
    RainbowFishSerializer.writeV1(fishV1, "fish1.out")
    // Read V1
    val deserializedRainbowFishV1 = RainbowFishSerializer.readV1("fish1.out")
    logger.info {
        "deserializedFishV1 name=${deserializedRainbowFishV1.name} age=${deserializedRainbowFishV1.age} " +
            "length=${deserializedRainbowFishV1.lengthMeters} weight=${deserializedRainbowFishV1.weightTons}"
    }
    // Write V2
    val fishV2 = RainbowFishV2("Scar", 5, 12, 15, sleeping = true, hungry = true, angry = true)
    logger.info {
        "fishV2 name=${fishV2.name} age=${fishV2.age} length=${fishV2.lengthMeters} " +
            "weight=${fishV2.weightTons} sleeping=${fishV2.sleeping} hungry=${fishV2.hungry} angry=${fishV2.angry}"
    }
    RainbowFishSerializer.writeV2(fishV2, "fish2.out")
    // Read V2 with V1 method
    val deserializedFishV2 = RainbowFishSerializer.readV1("fish2.out")
    logger.info {
        "deserializedFishV2 name=${deserializedFishV2.name} age=${deserializedFishV2.age} " +
            "length=${deserializedFishV2.lengthMeters} weight=${deserializedFishV2.weightTons}"
    }
}
