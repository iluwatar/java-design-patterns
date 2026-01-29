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

// ABOUTME: Main application demonstrating the Bytecode design pattern.
// ABOUTME: Shows how bytecode instructions can manipulate wizard properties through a virtual machine.
package com.iluwatar.bytecode

import com.iluwatar.bytecode.util.InstructionConverterUtil

private const val LITERAL_0 = "LITERAL 0"
private const val HEALTH_PATTERN = "%s_HEALTH"
private const val GET_AGILITY = "GET_AGILITY"
private const val GET_WISDOM = "GET_WISDOM"
private const val ADD = "ADD"
private const val LITERAL_2 = "LITERAL 2"
private const val DIVIDE = "DIVIDE"

/**
 * The intention of Bytecode pattern is to give behavior the flexibility of data by encoding it as
 * instructions for a virtual machine. An instruction set defines the low-level operations that can
 * be performed. A series of instructions is encoded as a sequence of bytes. A virtual machine
 * executes these instructions one at a time, using a stack for intermediate values. By combining
 * instructions, complex high-level behavior can be defined.
 *
 * This pattern should be used when there is a need to define high number of behaviours and
 * implementation engine is not a good choice because It is too lowe level Iterating on it takes too
 * long due to slow compile times or other tooling issues. It has too much trust. If you want to
 * ensure the behavior being defined can't break the game, you need to sandbox it from the rest of
 * the codebase.
 */
fun main() {
    val vm = VirtualMachine(Wizard(45, 7, 11, 0, 0), Wizard(36, 18, 8, 0, 0))

    vm.execute(InstructionConverterUtil.convertToByteCode(LITERAL_0))
    vm.execute(InstructionConverterUtil.convertToByteCode(LITERAL_0))
    vm.execute(InstructionConverterUtil.convertToByteCode(String.format(HEALTH_PATTERN, "GET")))
    vm.execute(InstructionConverterUtil.convertToByteCode(LITERAL_0))
    vm.execute(InstructionConverterUtil.convertToByteCode(GET_AGILITY))
    vm.execute(InstructionConverterUtil.convertToByteCode(LITERAL_0))
    vm.execute(InstructionConverterUtil.convertToByteCode(GET_WISDOM))
    vm.execute(InstructionConverterUtil.convertToByteCode(ADD))
    vm.execute(InstructionConverterUtil.convertToByteCode(LITERAL_2))
    vm.execute(InstructionConverterUtil.convertToByteCode(DIVIDE))
    vm.execute(InstructionConverterUtil.convertToByteCode(ADD))
    vm.execute(InstructionConverterUtil.convertToByteCode(String.format(HEALTH_PATTERN, "SET")))
}