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

import com.iluwatar.bytecode.util.InstructionConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The intention of Bytecode pattern is to give behavior the flexibility of data by encoding it as
 * instructions for a virtual machine. An instruction set defines the low-level operations that can
 * be performed. A series of instructions is encoded as a sequence of bytes. A virtual machine
 * executes these instructions one at a time, using a stack for intermediate values. By combining
 * instructions, complex high-level behavior can be defined.
 *
 * <p>This pattern should be used when there is a need to define high number of behaviours and
 * implementation engine is not a good choice because It is too lowe level Iterating on it takes too
 * long due to slow compile times or other tooling issues. It has too much trust. If you want to
 * ensure the behavior being defined can’t break the game, you need to sandbox it from the rest of
 * the codebase.
 */
public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Main app method.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    var wizard = new Wizard();
    wizard.setHealth(45);
    wizard.setAgility(7);
    wizard.setWisdom(11);

    var vm = new VirtualMachine();
    vm.getWizards()[0] = wizard;

    interpretInstruction("LITERAL 0", vm);
    interpretInstruction("LITERAL 0", vm);
    interpretInstruction("GET_HEALTH", vm);
    interpretInstruction("LITERAL 0", vm);
    interpretInstruction("GET_AGILITY", vm);
    interpretInstruction("LITERAL 0", vm);
    interpretInstruction("GET_WISDOM ", vm);
    interpretInstruction("ADD", vm);
    interpretInstruction("LITERAL 2", vm);
    interpretInstruction("DIVIDE", vm);
    interpretInstruction("ADD", vm);
    interpretInstruction("SET_HEALTH", vm);
  }

  private static void interpretInstruction(String instruction, VirtualMachine vm) {
    vm.execute(InstructionConverterUtil.convertToByteCode(instruction));
    var stack = vm.getStack();
    LOGGER.info(instruction + String.format("%" + (12 - instruction.length()) + "s", "") + stack);
  }
}
