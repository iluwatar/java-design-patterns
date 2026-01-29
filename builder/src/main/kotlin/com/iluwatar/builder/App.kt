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
package com.iluwatar.builder

// ABOUTME: Entry point demonstrating the Builder pattern using Kotlin named arguments.
// ABOUTME: Creates Hero objects with various attributes via named parameters with defaults.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The intention of the Builder pattern is to find a solution to the telescoping constructor
 * antipattern. The telescoping constructor antipattern occurs when the increase of object
 * constructor parameter combination leads to an exponential list of constructors. Instead of using
 * numerous constructors, the builder pattern uses another object, a builder, that receives each
 * initialization parameter step by step and then returns the resulting constructed object at once.
 *
 * The Builder pattern has another benefit. It can be used for objects that contain flat data
 * (html code, SQL query, X.509 certificate...), that is to say, data that can't be easily edited.
 * This type of data cannot be edited step by step and must be edited at once. The best way to
 * construct such an object is to use a builder class.
 *
 * In this Kotlin example, the Builder pattern is replaced by idiomatic named arguments with
 * default values. We build [Hero] objects by specifying only the parameters we need.
 */
fun main() {
    val mage =
        Hero(
            profession = Profession.MAGE,
            name = "Riobard",
            hairColor = HairColor.BLACK,
            weapon = Weapon.DAGGER,
        )
    logger.info { mage.toString() }

    val warrior =
        Hero(
            profession = Profession.WARRIOR,
            name = "Amberjill",
            hairColor = HairColor.BLOND,
            hairType = HairType.LONG_CURLY,
            armor = Armor.CHAIN_MAIL,
            weapon = Weapon.SWORD,
        )
    logger.info { warrior.toString() }

    val thief =
        Hero(
            profession = Profession.THIEF,
            name = "Desmond",
            hairType = HairType.BALD,
            weapon = Weapon.BOW,
        )
    logger.info { thief.toString() }
}