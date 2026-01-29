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

// ABOUTME: Utility object for converting string-based instructions to bytecode format.
// ABOUTME: Handles parsing and validation of instruction strings and numeric literals.
package com.iluwatar.bytecode.util

import com.iluwatar.bytecode.Instruction

/**
 * Utility class used for instruction validation and conversion.
 */
object InstructionConverterUtil {
    /**
     * Converts instructions represented as String.
     *
     * @param instructions to convert
     * @return array of int representing bytecode
     */
    fun convertToByteCode(instructions: String?): IntArray {
        if (instructions.isNullOrBlank()) {
            return IntArray(0)
        }

        val splitInstructions = instructions.trim().split(" ")
        val bytecode = IntArray(splitInstructions.size)
        for (i in splitInstructions.indices) {
            val part = splitInstructions[i]
            when {
                isValidInstruction(part) -> {
                    bytecode[i] = Instruction.valueOf(part).intValue
                }
                isValidInt(part) -> {
                    bytecode[i] = part.toInt()
                }
                else -> {
                    throw IllegalArgumentException("Invalid instruction or number: $part")
                }
            }
        }

        return bytecode
    }

    private fun isValidInstruction(instruction: String): Boolean =
        try {
            Instruction.valueOf(instruction)
            true
        } catch (e: IllegalArgumentException) {
            false
        }

    private fun isValidInt(value: String): Boolean = value.toIntOrNull() != null
}