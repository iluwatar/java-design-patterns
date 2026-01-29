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

package com.iluwatar.multiton

// ABOUTME: Entry point demonstrating the Multiton design pattern with both map-based and enum-based approaches.
// ABOUTME: Logs each Nazgul instance retrieved from the eagerly initialized multiton and the enum multiton.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Whereas Singleton design pattern introduces single globally accessible object, the Multiton
 * pattern defines many globally accessible objects. The client asks for the correct instance from
 * the Multiton by passing an enumeration as a parameter.
 *
 * There is more than one way to implement the multiton design pattern. In the first example
 * [Nazgul] is the Multiton, and we can ask single [Nazgul] from it using [NazgulName]. The
 * [Nazgul]s are statically initialized and stored in a concurrent hash map.
 *
 * In the enum implementation [NazgulEnum] is the multiton. It is static and immutable
 * because of the way Kotlin supports enums.
 */
fun main() {
    // eagerly initialized multiton
    logger.info { "Printing out eagerly initialized multiton contents" }
    NazgulName.entries.forEach { name ->
        logger.info { "$name=${Nazgul.getInstance(name)}" }
    }

    // enum multiton
    logger.info { "Printing out enum-based multiton contents" }
    NazgulEnum.entries.forEach { nazgul ->
        logger.info { "$nazgul" }
    }
}
