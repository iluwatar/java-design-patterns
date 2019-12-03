/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.bytecode;

/**
 * Representation of instructions understandable by virtual machine.
 */
public enum Instruction {

  LITERAL(1),
  SET_HEALTH(2),
  SET_WISDOM(3),
  SET_AGILITY(4),
  PLAY_SOUND(5),
  SPAWN_PARTICLES(6),
  GET_HEALTH(7),
  GET_AGILITY(8),
  GET_WISDOM(9),
  ADD(10),
  DIVIDE(11);

  private final int value;

  Instruction(int value) {
    this.value = value;
  }

  public int getIntValue() {
    return value;
  }

  /**
   * Converts integer value to Instruction.
   *
   * @param value value of instruction
   * @return representation of the instruction
   */
  public static Instruction getInstruction(int value) {
    for (var i = 0; i < Instruction.values().length; i++) {
      if (Instruction.values()[i].getIntValue() == value) {
        return Instruction.values()[i];
      }
    }
    throw new IllegalArgumentException("Invalid instruction value");
  }
}
