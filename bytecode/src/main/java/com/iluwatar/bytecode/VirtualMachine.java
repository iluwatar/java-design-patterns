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

import java.util.Stack;

/**
 * Implementation of virtual machine.
 */
public class VirtualMachine {

  private Stack<Integer> stack = new Stack<>();

  private Wizard[] wizards = new Wizard[2];

  /**
   * Constructor.
   */
  public VirtualMachine() {
    for (var i = 0; i < wizards.length; i++) {
      wizards[i] = new Wizard();
    }
  }

  /**
   * Executes provided bytecode.
   *
   * @param bytecode to execute
   */
  public void execute(int[] bytecode) {
    for (var i = 0; i < bytecode.length; i++) {
      Instruction instruction = Instruction.getInstruction(bytecode[i]);
      switch (instruction) {
        case LITERAL:
          // Read the next byte from the bytecode.
          int value = bytecode[++i];
          stack.push(value);
          break;
        case SET_AGILITY:
          var amount = stack.pop();
          var wizard = stack.pop();
          setAgility(wizard, amount);
          break;
        case SET_WISDOM:
          amount = stack.pop();
          wizard = stack.pop();
          setWisdom(wizard, amount);
          break;
        case SET_HEALTH:
          amount = stack.pop();
          wizard = stack.pop();
          setHealth(wizard, amount);
          break;
        case GET_HEALTH:
          wizard = stack.pop();
          stack.push(getHealth(wizard));
          break;
        case GET_AGILITY:
          wizard = stack.pop();
          stack.push(getAgility(wizard));
          break;
        case GET_WISDOM:
          wizard = stack.pop();
          stack.push(getWisdom(wizard));
          break;
        case ADD:
          var a = stack.pop();
          var b = stack.pop();
          stack.push(a + b);
          break;
        case DIVIDE:
          a = stack.pop();
          b = stack.pop();
          stack.push(b / a);
          break;
        case PLAY_SOUND:
          wizard = stack.pop();
          getWizards()[wizard].playSound();
          break;
        case SPAWN_PARTICLES:
          wizard = stack.pop();
          getWizards()[wizard].spawnParticles();
          break;
        default:
          throw new IllegalArgumentException("Invalid instruction value");
      }
    }
  }

  public Stack<Integer> getStack() {
    return stack;
  }

  public void setHealth(int wizard, int amount) {
    wizards[wizard].setHealth(amount);
  }

  public void setWisdom(int wizard, int amount) {
    wizards[wizard].setWisdom(amount);
  }

  public void setAgility(int wizard, int amount) {
    wizards[wizard].setAgility(amount);
  }

  public int getHealth(int wizard) {
    return wizards[wizard].getHealth();
  }

  public int getWisdom(int wizard) {
    return wizards[wizard].getWisdom();
  }

  public int getAgility(int wizard) {
    return wizards[wizard].getAgility();
  }

  public Wizard[] getWizards() {
    return wizards;
  }
}
