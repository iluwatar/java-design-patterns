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
package com.iluwatar.bytecode.util;

import com.iluwatar.bytecode.Instruction;

/**
 * Utility class used for instruction validation and conversion.
 */
public class InstructionConverterUtil {
  /**
   * Converts instructions represented as String.
   *
   * @param instructions to convert
   * @return array of int representing bytecode
   */
  public static int[] convertToByteCode(String instructions) {
    if (instructions == null || instructions.trim().length() == 0) {
      return new int[0];
    }

    var splitedInstructions = instructions.trim().split(" ");
    var bytecode = new int[splitedInstructions.length];
    for (var i = 0; i < splitedInstructions.length; i++) {
      if (isValidInstruction(splitedInstructions[i])) {
        bytecode[i] = Instruction.valueOf(splitedInstructions[i]).getIntValue();
      } else if (isValidInt(splitedInstructions[i])) {
        bytecode[i] = Integer.parseInt(splitedInstructions[i]);
      } else {
        var errorMessage = "Invalid instruction or number: " + splitedInstructions[i];
        throw new IllegalArgumentException(errorMessage);
      }
    }

    return bytecode;
  }

  private static boolean isValidInstruction(String instruction) {
    try {
      Instruction.valueOf(instruction);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private static boolean isValidInt(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
