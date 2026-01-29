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

// ABOUTME: Entry point demonstrating the Step Builder pattern for character creation.
// ABOUTME: Shows how to build warrior, mage, and thief characters using the fluent step-based API.
package com.iluwatar.stepbuilder

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Step Builder Pattern
 *
 * **Intent**
 * An extension of the Builder pattern that fully guides the user through the creation of the object
 * with no chances of confusion.
 * The user experience will be much more improved by the fact that he will only see the next step
 * methods available, NO build method until is the right time to build the object.
 *
 * **Implementation**
 * The concept is simple:
 * - Write creational steps inner classes or interfaces where each method knows what can be
 *   displayed next.
 * - Implement all your steps interfaces in an inner static class.
 * - Last step is the BuildStep, in charge of creating the object you need to build.
 *
 * **Applicability**
 * Use the Step Builder pattern when the algorithm for creating a complex object should be
 * independent of the parts that make up the object and how they're assembled the construction
 * process must allow different representations for the object that's constructed when in the
 * process of constructing the order is important.
 *
 * @see [Step Builder Pattern](http://rdafbn.blogspot.co.uk/2012/07/step-builder-pattern_28.html)
 */
fun main() {
    val warrior = CharacterStepBuilder.newBuilder()
        .name("Amberjill")
        .fighterClass("Paladin")
        .withWeapon("Sword")
        .noAbilities()
        .build()

    logger.info { warrior.toString() }

    val mage = CharacterStepBuilder.newBuilder()
        .name("Riobard")
        .wizardClass("Sorcerer")
        .withSpell("Fireball")
        .withAbility("Fire Aura")
        .withAbility("Teleport")
        .noMoreAbilities()
        .build()

    logger.info { mage.toString() }

    val thief = CharacterStepBuilder.newBuilder()
        .name("Desmond")
        .fighterClass("Rogue")
        .noWeapon()
        .build()

    logger.info { thief.toString() }
}
