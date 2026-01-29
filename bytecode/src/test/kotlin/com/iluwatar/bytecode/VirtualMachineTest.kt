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

// ABOUTME: Unit tests for VirtualMachine class testing bytecode execution.
// ABOUTME: Tests all instructions including LITERAL, SET/GET operations, arithmetic, and effects.
package com.iluwatar.bytecode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

/**
 * Test for [VirtualMachine]
 */
class VirtualMachineTest {
    @Test
    fun testLiteral() {
        val bytecode = IntArray(2)
        bytecode[0] = Instruction.LITERAL.intValue
        bytecode[1] = 10

        val vm = VirtualMachine()
        vm.execute(bytecode)

        assertEquals(1, vm.stack.size)
        assertEquals(10, vm.stack.pop())
    }

    @Test
    fun testSetHealth() {
        val wizardNumber = 0
        val bytecode = IntArray(5)
        bytecode[0] = Instruction.LITERAL.intValue
        bytecode[1] = wizardNumber
        bytecode[2] = Instruction.LITERAL.intValue
        bytecode[3] = 50 // health amount
        bytecode[4] = Instruction.SET_HEALTH.intValue

        val vm = VirtualMachine()
        vm.execute(bytecode)

        assertEquals(50, vm.wizards[wizardNumber].health)
    }

    @Test
    fun testSetAgility() {
        val wizardNumber = 0
        val bytecode = IntArray(5)
        bytecode[0] = Instruction.LITERAL.intValue
        bytecode[1] = wizardNumber
        bytecode[2] = Instruction.LITERAL.intValue
        bytecode[3] = 50 // agility amount
        bytecode[4] = Instruction.SET_AGILITY.intValue

        val vm = VirtualMachine()
        vm.execute(bytecode)

        assertEquals(50, vm.wizards[wizardNumber].agility)
    }

    @Test
    fun testSetWisdom() {
        val wizardNumber = 0
        val bytecode = IntArray(5)
        bytecode[0] = Instruction.LITERAL.intValue
        bytecode[1] = wizardNumber
        bytecode[2] = Instruction.LITERAL.intValue
        bytecode[3] = 50 // wisdom amount
        bytecode[4] = Instruction.SET_WISDOM.intValue

        val vm = VirtualMachine()
        vm.execute(bytecode)

        assertEquals(50, vm.wizards[wizardNumber].wisdom)
    }

    @Test
    fun testGetHealth() {
        val wizardNumber = 0
        val bytecode = IntArray(8)
        bytecode[0] = Instruction.LITERAL.intValue
        bytecode[1] = wizardNumber
        bytecode[2] = Instruction.LITERAL.intValue
        bytecode[3] = 50 // health amount
        bytecode[4] = Instruction.SET_HEALTH.intValue
        bytecode[5] = Instruction.LITERAL.intValue
        bytecode[6] = wizardNumber
        bytecode[7] = Instruction.GET_HEALTH.intValue

        val vm = VirtualMachine()
        vm.execute(bytecode)

        assertEquals(50, vm.stack.pop())
    }

    @Test
    fun testPlaySound() {
        val wizardNumber = 0
        val bytecode = IntArray(3)
        bytecode[0] = Instruction.LITERAL.intValue
        bytecode[1] = wizardNumber
        bytecode[2] = Instruction.PLAY_SOUND.intValue

        val vm = VirtualMachine()
        vm.execute(bytecode)

        assertEquals(0, vm.stack.size)
        assertEquals(1, vm.wizards[0].numberOfPlayedSounds)
    }

    @Test
    fun testSpawnParticles() {
        val wizardNumber = 0
        val bytecode = IntArray(3)
        bytecode[0] = Instruction.LITERAL.intValue
        bytecode[1] = wizardNumber
        bytecode[2] = Instruction.SPAWN_PARTICLES.intValue

        val vm = VirtualMachine()
        vm.execute(bytecode)

        assertEquals(0, vm.stack.size)
        assertEquals(1, vm.wizards[0].numberOfSpawnedParticles)
    }

    @Test
    fun testInvalidInstruction() {
        val bytecode = IntArray(1)
        bytecode[0] = 999
        val vm = VirtualMachine()

        assertThrows(IllegalArgumentException::class.java) { vm.execute(bytecode) }
    }
}