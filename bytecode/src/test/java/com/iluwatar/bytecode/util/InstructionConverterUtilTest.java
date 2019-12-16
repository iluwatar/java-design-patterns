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

package com.iluwatar.bytecode.util;

import com.iluwatar.bytecode.Instruction;
import com.iluwatar.bytecode.util.InstructionConverterUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for {@Link InstructionConverterUtil}
 */
public class InstructionConverterUtilTest {
  @Test
  public void testEmptyInstruction() {
    var instruction = "";

    var bytecode = InstructionConverterUtil.convertToByteCode(instruction);

    Assertions.assertEquals(0, bytecode.length);
  }

  @Test
  public void testInstructions() {
    var instructions = "LITERAL 35 SET_HEALTH SET_WISDOM SET_AGILITY PLAY_SOUND"
        + " SPAWN_PARTICLES GET_HEALTH ADD DIVIDE";

    var bytecode = InstructionConverterUtil.convertToByteCode(instructions);

    Assertions.assertEquals(10, bytecode.length);
    Assertions.assertEquals(Instruction.LITERAL.getIntValue(), bytecode[0]);
    Assertions.assertEquals(35, bytecode[1]);
    Assertions.assertEquals(Instruction.SET_HEALTH.getIntValue(), bytecode[2]);
    Assertions.assertEquals(Instruction.SET_WISDOM.getIntValue(), bytecode[3]);
    Assertions.assertEquals(Instruction.SET_AGILITY.getIntValue(), bytecode[4]);
    Assertions.assertEquals(Instruction.PLAY_SOUND.getIntValue(), bytecode[5]);
    Assertions.assertEquals(Instruction.SPAWN_PARTICLES.getIntValue(), bytecode[6]);
    Assertions.assertEquals(Instruction.GET_HEALTH.getIntValue(), bytecode[7]);
    Assertions.assertEquals(Instruction.ADD.getIntValue(), bytecode[8]);
    Assertions.assertEquals(Instruction.DIVIDE.getIntValue(), bytecode[9]);
  }

}
