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
package com.iluwatar.abstractfactory

// ABOUTME: Entry point demonstrating the Abstract Factory design pattern.
// ABOUTME: Creates Elf and Orc kingdoms using their respective factory implementations.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Abstract Factory pattern provides a way to encapsulate a group of individual factories that
 * have a common theme without specifying their concrete classes. In normal usage, the client
 * software creates a concrete implementation of the abstract factory and then uses the generic
 * interface of the factory to create the concrete objects that are part of the theme. The client
 * does not know (or care) which concrete objects it gets from each of these internal factories,
 * since it uses only the generic interfaces of their products. This pattern separates the details
 * of implementation of a set of objects from their general usage and relies on object composition,
 * as object creation is implemented in methods exposed in the factory interface.
 *
 * The essence of the Abstract Factory pattern is a factory interface ([KingdomFactory])
 * and its implementations ([ElfKingdomFactory], [OrcKingdomFactory]). The example uses
 * both concrete implementations to create a king, a castle, and an army.
 */
val kingdom = Kingdom()

/**
 * Creates kingdom.
 *
 * @param kingdomType type of Kingdom
 */
fun createKingdom(kingdomType: Kingdom.FactoryMaker.KingdomType) {
    val kingdomFactory = Kingdom.FactoryMaker.makeFactory(kingdomType)
    kingdom.king = kingdomFactory.createKing()
    kingdom.castle = kingdomFactory.createCastle()
    kingdom.army = kingdomFactory.createArmy()
}

/** Program entry point. */
fun main() {
    logger.info { "elf kingdom" }
    createKingdom(Kingdom.FactoryMaker.KingdomType.ELF)
    logger.info { kingdom.army?.description }
    logger.info { kingdom.castle?.description }
    logger.info { kingdom.king?.description }

    logger.info { "orc kingdom" }
    createKingdom(Kingdom.FactoryMaker.KingdomType.ORC)
    logger.info { kingdom.army?.description }
    logger.info { kingdom.castle?.description }
    logger.info { kingdom.king?.description }
}