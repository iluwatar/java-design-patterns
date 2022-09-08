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
package com.iluwatar.bytecode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representation of instructions understandable by virtual machine.
 */
@AllArgsConstructor
@Getter
public enum Instruction {

  LITERAL(1),         // e.g. "LITERAL 0", push 0 to stack
  SET_HEALTH(2),      // e.g. "SET_HEALTH", pop health and wizard number, call set health
  SET_WISDOM(3),      // e.g. "SET_WISDOM", pop wisdom and wizard number, call set wisdom
  SET_AGILITY(4),     // e.g. "SET_AGILITY", pop agility and wizard number, call set agility
  PLAY_SOUND(5),      // e.g. "PLAY_SOUND", pop value as wizard number, call play sound
  SPAWN_PARTICLES(6), // e.g. "SPAWN_PARTICLES", pop value as wizard number, call spawn particles
  GET_HEALTH(7),      // e.g. "GET_HEALTH", pop value as wizard number, push wizard's health
  GET_AGILITY(8),     // e.g. "GET_AGILITY", pop value as wizard number, push wizard's agility
  GET_WISDOM(9),      // e.g. "GET_WISDOM", pop value as wizard number, push wizard's wisdom
  ADD(10),            // e.g. "ADD", pop 2 values, push their sum
  DIVIDE(11);         // e.g. "DIVIDE", pop 2 values, push their division

  private final int intValue;

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
