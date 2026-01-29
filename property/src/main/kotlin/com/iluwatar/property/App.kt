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
package com.iluwatar.property

// ABOUTME: Entry point demonstrating the Property (prototype inheritance) pattern.
// ABOUTME: Shows character creation with stat inheritance through prototype chains.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Property pattern is also known as Prototype inheritance.
 *
 * In prototype inheritance instead of classes, as opposite to Java class inheritance, objects
 * are used to create other objects and object hierarchies. Hierarchies are created using
 * prototype chain through delegation: every object has link to parent object. Any base (parent)
 * object can be amended at runtime (by adding or removal of some property), and all child objects
 * will be affected as result.
 *
 * In this example we demonstrate [Character] instantiation using the Property pattern.
 */
fun main() {
    /* set up */
    val charProto = Character()
    charProto.set(Stats.STRENGTH, 10)
    charProto.set(Stats.AGILITY, 10)
    charProto.set(Stats.ARMOR, 10)
    charProto.set(Stats.ATTACK_POWER, 10)

    val mageProto = Character(Character.Type.MAGE, charProto)
    mageProto.set(Stats.INTELLECT, 15)
    mageProto.set(Stats.SPIRIT, 10)

    val warProto = Character(Character.Type.WARRIOR, charProto)
    warProto.set(Stats.RAGE, 15)
    warProto.set(Stats.ARMOR, 15) // boost default armor for warrior

    val rogueProto = Character(Character.Type.ROGUE, charProto)
    rogueProto.set(Stats.ENERGY, 15)
    rogueProto.set(Stats.AGILITY, 15) // boost default agility for rogue

    /* usage */
    val mag = Character("Player_1", mageProto)
    mag.set(Stats.ARMOR, 8)
    logger.info { mag.toString() }

    val warrior = Character("Player_2", warProto)
    logger.info { warrior.toString() }

    val rogue = Character("Player_3", rogueProto)
    logger.info { rogue.toString() }

    val rogueDouble = Character("Player_4", rogue)
    rogueDouble.set(Stats.ATTACK_POWER, 12)
    logger.info { rogueDouble.toString() }
}
