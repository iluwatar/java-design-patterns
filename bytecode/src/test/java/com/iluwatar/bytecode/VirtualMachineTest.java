/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import static com.iluwatar.bytecode.Instruction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test for {@link VirtualMachine}
 */
class VirtualMachineTest {

  @Test
  void testLiteral() {
    var bytecode = new int[2];
    bytecode[0] = LITERAL.getIntValue();
    bytecode[1] = 10;

    var vm = new VirtualMachine();
    vm.execute(bytecode);

    assertEquals(1, vm.getStack().size());
    assertEquals(Integer.valueOf(10), vm.getStack().pop());
  }

  @Test
  void testSetHealth() {
    var wizardNumber = 0;
    var bytecode = new int[5];
    bytecode[0] = LITERAL.getIntValue();
    bytecode[1] = wizardNumber;
    bytecode[2] = LITERAL.getIntValue();
    bytecode[3] = 50;                        // health amount
    bytecode[4] = SET_HEALTH.getIntValue();

    var vm = new VirtualMachine();
    vm.execute(bytecode);

    assertEquals(50, vm.getWizards()[wizardNumber].getHealth());
  }

  @Test
  void testSetAgility() {
    var wizardNumber = 0;
    var bytecode = new int[5];
    bytecode[0] = LITERAL.getIntValue();
    bytecode[1] = wizardNumber;
    bytecode[2] = LITERAL.getIntValue();
    bytecode[3] = 50;                        // agility amount
    bytecode[4] = SET_AGILITY.getIntValue();

    var vm = new VirtualMachine();
    vm.execute(bytecode);

    assertEquals(50, vm.getWizards()[wizardNumber].getAgility());
  }

  @Test
  void testSetWisdom() {
    var wizardNumber = 0;
    var bytecode = new int[5];
    bytecode[0] = LITERAL.getIntValue();
    bytecode[1] = wizardNumber;
    bytecode[2] = LITERAL.getIntValue();
    bytecode[3] = 50;                        // wisdom amount
    bytecode[4] = SET_WISDOM.getIntValue();

    var vm = new VirtualMachine();
    vm.execute(bytecode);

    assertEquals(50, vm.getWizards()[wizardNumber].getWisdom());
  }

  @Test
  void testGetHealth() {
    var wizardNumber = 0;
    var bytecode = new int[8];
    bytecode[0] = LITERAL.getIntValue();
    bytecode[1] = wizardNumber;
    bytecode[2] = LITERAL.getIntValue();
    bytecode[3] = 50;                        // health amount
    bytecode[4] = SET_HEALTH.getIntValue();
    bytecode[5] = LITERAL.getIntValue();
    bytecode[6] = wizardNumber;
    bytecode[7] = GET_HEALTH.getIntValue();

    var vm = new VirtualMachine();
    vm.execute(bytecode);

    assertEquals(Integer.valueOf(50), vm.getStack().pop());
  }

  @Test
  void testPlaySound() {
    var wizardNumber = 0;
    var bytecode = new int[3];
    bytecode[0] = LITERAL.getIntValue();
    bytecode[1] = wizardNumber;
    bytecode[2] = PLAY_SOUND.getIntValue();

    var vm = new VirtualMachine();
    vm.execute(bytecode);

    assertEquals(0, vm.getStack().size());
    assertEquals(1, vm.getWizards()[0].getNumberOfPlayedSounds());
  }

  @Test
  void testSpawnParticles() {
    var wizardNumber = 0;
    var bytecode = new int[3];
    bytecode[0] = LITERAL.getIntValue();
    bytecode[1] = wizardNumber;
    bytecode[2] = SPAWN_PARTICLES.getIntValue();

    var vm = new VirtualMachine();
    vm.execute(bytecode);

    assertEquals(0, vm.getStack().size());
    assertEquals(1, vm.getWizards()[0].getNumberOfSpawnedParticles());
  }

  @Test
  void testInvalidInstruction() {
    var bytecode = new int[1];
    bytecode[0] = 999;
    var vm = new VirtualMachine();

    assertThrows(IllegalArgumentException.class, () -> vm.execute(bytecode));
  }
}
