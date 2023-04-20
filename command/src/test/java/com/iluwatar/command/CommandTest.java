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
package com.iluwatar.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * The Command pattern is a behavioral design pattern in which an object is used to encapsulate all
 * information needed to perform an action or trigger an event at a later time. This information
 * includes the method name, the object that owns the method and values for the method parameters.
 *
 * <p>Four terms always associated with the command pattern are command, receiver, invoker and
 * client. A command object (spell) knows about the receiver (target) and invokes a method of the
 * receiver.Values for parameters of the receiver method are stored in the command. The receiver
 * then does the work. An invoker object (wizard) knows how to execute a command, and optionally
 * does bookkeeping about the command execution. The invoker does not know anything about a concrete
 * command, it knows only about command interface. Both an invoker object and several command
 * objects are held by a client object (app). The client decides which commands to execute at which
 * points. To execute a command, it passes the command object to the invoker object.
 */
class CommandTest {

  private static final String GOBLIN = "Goblin";

  /**
   * This test verifies that when the wizard casts spells on the goblin. The wizard keeps track of
   * the previous spells cast, so it is easy to undo them. In addition, it also verifies that the
   * wizard keeps track of the spells undone, so they can be redone.
   */
  @Test
  void testCommand() {

    var wizard = new Wizard();
    var goblin = new Goblin();

    wizard.castSpell(goblin::changeSize);
    verifyGoblin(goblin, GOBLIN, Size.SMALL, Visibility.VISIBLE);

    wizard.castSpell(goblin::changeVisibility);
    verifyGoblin(goblin, GOBLIN, Size.SMALL, Visibility.INVISIBLE);

    wizard.undoLastSpell();
    verifyGoblin(goblin, GOBLIN, Size.SMALL, Visibility.VISIBLE);

    wizard.undoLastSpell();
    verifyGoblin(goblin, GOBLIN, Size.NORMAL, Visibility.VISIBLE);

    wizard.redoLastSpell();
    verifyGoblin(goblin, GOBLIN, Size.SMALL, Visibility.VISIBLE);

    wizard.redoLastSpell();
    verifyGoblin(goblin, GOBLIN, Size.SMALL, Visibility.INVISIBLE);
  }

  /**
   * This method asserts that the passed goblin object has the name as expectedName, size as
   * expectedSize and visibility as expectedVisibility.
   *
   * @param goblin             a goblin object whose state is to be verified against other
   *                           parameters
   * @param expectedName       expectedName of the goblin
   * @param expectedSize       expected size of the goblin
   * @param expectedVisibility expected visibility of the goblin
   */
  private void verifyGoblin(Goblin goblin, String expectedName, Size expectedSize,
                            Visibility expectedVisibility) {
    assertEquals(expectedName, goblin.toString(), "Goblin's name must be same as expectedName");
    assertEquals(expectedSize, goblin.getSize(), "Goblin's size must be same as expectedSize");
    assertEquals(expectedVisibility, goblin.getVisibility(),
        "Goblin's visibility must be same as expectedVisibility");
  }
}
