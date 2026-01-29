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

// ABOUTME: Entry point demonstrating the Extension Objects pattern with military units.
// ABOUTME: Creates units and checks for extensions, invoking ready methods when available.

import abstractextensions.CommanderExtension
import abstractextensions.SergeantExtension
import abstractextensions.SoldierExtension
import io.github.oshai.kotlinlogging.KotlinLogging
import units.CommanderUnit
import units.SergeantUnit
import units.SoldierUnit
import units.Unit

private val logger = KotlinLogging.logger {}

/**
 * Anticipate that an object's interface needs to be extended in the future. Additional interfaces
 * are defined by extension objects.
 */
fun main() {
    // Create 3 different units
    val soldierUnit = SoldierUnit("SoldierUnit1")
    val sergeantUnit = SergeantUnit("SergeantUnit1")
    val commanderUnit = CommanderUnit("CommanderUnit1")

    // check for each unit to have an extension
    checkExtensionsForUnit(soldierUnit)
    checkExtensionsForUnit(sergeantUnit)
    checkExtensionsForUnit(commanderUnit)
}

private fun checkExtensionsForUnit(unit: Unit) {
    val name = unit.name

    var extension = "SoldierExtension"
    val soldierExtension = unit.getUnitExtension(extension) as? SoldierExtension
    if (soldierExtension != null) {
        soldierExtension.soldierReady()
    } else {
        logger.info { "$name without $extension" }
    }

    extension = "SergeantExtension"
    val sergeantExtension = unit.getUnitExtension(extension) as? SergeantExtension
    if (sergeantExtension != null) {
        sergeantExtension.sergeantReady()
    } else {
        logger.info { "$name without $extension" }
    }

    extension = "CommanderExtension"
    val commanderExtension = unit.getUnitExtension(extension) as? CommanderExtension
    if (commanderExtension != null) {
        commanderExtension.commanderReady()
    } else {
        logger.info { "$name without $extension" }
    }
}
