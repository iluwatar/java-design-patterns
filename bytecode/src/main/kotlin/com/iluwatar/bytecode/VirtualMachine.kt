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

// ABOUTME: Virtual machine implementation that executes bytecode instructions.
// ABOUTME: Uses a stack-based architecture to manipulate wizard properties.
package com.iluwatar.bytecode

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.Stack
import java.util.concurrent.ThreadLocalRandom

private val logger = KotlinLogging.logger {}

/**
 * Implementation of virtual machine.
 */
class VirtualMachine {
    val stack: Stack<Int> = Stack()

    val wizards: Array<Wizard> =
        arrayOf(
            Wizard(randomInt(3, 32), randomInt(3, 32), randomInt(3, 32), 0, 0),
            Wizard(randomInt(3, 32), randomInt(3, 32), randomInt(3, 32), 0, 0),
        )

    /**
     * Constructor taking the wizards as arguments.
     */
    constructor(wizard1: Wizard, wizard2: Wizard) {
        wizards[0] = wizard1
        wizards[1] = wizard2
    }

    /**
     * No-args constructor.
     */
    constructor()

    /**
     * Executes provided bytecode.
     *
     * @param bytecode to execute
     */
    fun execute(bytecode: IntArray) {
        var i = 0
        while (i < bytecode.size) {
            val instruction = Instruction.getInstruction(bytecode[i])
            when (instruction) {
                Instruction.LITERAL -> {
                    // Read the next byte from the bytecode.
                    val value = bytecode[++i]
                    // Push the next value to stack
                    stack.push(value)
                }
                Instruction.SET_AGILITY -> {
                    val amount = stack.pop()
                    val wizard = stack.pop()
                    setAgility(wizard, amount)
                }
                Instruction.SET_WISDOM -> {
                    val amount = stack.pop()
                    val wizard = stack.pop()
                    setWisdom(wizard, amount)
                }
                Instruction.SET_HEALTH -> {
                    val amount = stack.pop()
                    val wizard = stack.pop()
                    setHealth(wizard, amount)
                }
                Instruction.GET_HEALTH -> {
                    val wizard = stack.pop()
                    stack.push(getHealth(wizard))
                }
                Instruction.GET_AGILITY -> {
                    val wizard = stack.pop()
                    stack.push(getAgility(wizard))
                }
                Instruction.GET_WISDOM -> {
                    val wizard = stack.pop()
                    stack.push(getWisdom(wizard))
                }
                Instruction.ADD -> {
                    val a = stack.pop()
                    val b = stack.pop()
                    stack.push(a + b)
                }
                Instruction.DIVIDE -> {
                    val a = stack.pop()
                    val b = stack.pop()
                    stack.push(b / a)
                }
                Instruction.PLAY_SOUND -> {
                    val wizard = stack.pop()
                    wizards[wizard].playSound()
                }
                Instruction.SPAWN_PARTICLES -> {
                    val wizard = stack.pop()
                    wizards[wizard].spawnParticles()
                }
            }
            logger.info { "Executed ${instruction.name}, Stack contains $stack" }
            i++
        }
    }

    fun setHealth(
        wizard: Int,
        amount: Int,
    ) {
        wizards[wizard].health = amount
    }

    fun setWisdom(
        wizard: Int,
        amount: Int,
    ) {
        wizards[wizard].wisdom = amount
    }

    fun setAgility(
        wizard: Int,
        amount: Int,
    ) {
        wizards[wizard].agility = amount
    }

    fun getHealth(wizard: Int): Int = wizards[wizard].health

    fun getWisdom(wizard: Int): Int = wizards[wizard].wisdom

    fun getAgility(wizard: Int): Int = wizards[wizard].agility

    private fun randomInt(
        min: Int,
        max: Int,
    ): Int = ThreadLocalRandom.current().nextInt(min, max + 1)
}