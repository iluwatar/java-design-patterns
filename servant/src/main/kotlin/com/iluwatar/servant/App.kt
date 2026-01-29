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
package com.iluwatar.servant

// ABOUTME: Entry point demonstrating the Servant design pattern.
// ABOUTME: Shows how Servant objects (Jenkins, Travis) serve King and Queen royalty.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

private val jenkins = Servant("Jenkins")
private val travis = Servant("Travis")

/**
 * Servant offers some functionality to a group of classes without defining that functionality in
 * each of them. A Servant is a class whose instance provides methods that take care of a desired
 * service, while objects for which the servant does something, are taken as parameters.
 *
 * In this example [Servant] is serving [King] and [Queen].
 */
fun main() {
    scenario(jenkins, 1)
    scenario(travis, 0)
}

/** Can add a List with enum Actions for variable scenarios. */
fun scenario(servant: Servant, compliment: Int) {
    val k = King()
    val q = Queen()

    val guests = listOf(k, q)

    // feed
    servant.feed(k)
    servant.feed(q)
    // serve drinks
    servant.giveWine(k)
    servant.giveWine(q)
    // compliment
    servant.giveCompliments(guests[compliment])

    // outcome of the night
    guests.forEach { it.changeMood() }

    // check your luck
    if (servant.checkIfYouWillBeHanged(guests)) {
        logger.info { "${servant.name} will live another day" }
    } else {
        logger.info { "Poor ${servant.name}. His days are numbered" }
    }
}
