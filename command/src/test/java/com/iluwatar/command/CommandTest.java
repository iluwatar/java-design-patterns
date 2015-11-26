package com.iluwatar.command;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Command pattern is a behavioral design pattern in which an object is used to encapsulate all
 * information needed to perform an action or trigger an event at a later time. This information
 * includes the method name, the object that owns the method and values for the method parameters.
 * 
 * <p>Four terms always associated with the command pattern are command, receiver, invoker and 
 * client. A command object (spell) knows about the receiver (target) and invokes a method of 
 * the receiver.Values for parameters of the receiver method are stored in the command. The receiver
 * then does the work. An invoker object (wizard) knows how to execute a command, and optionally 
 * does bookkeeping about the command execution. The invoker does not know anything about a 
 * concrete command, it knows only about command interface. Both an invoker object and several 
 * command objects are held by a client object (app). The client decides which commands to execute
 * at which points. To execute a command, it passes the command object to the invoker object.
 */
public class CommandTest {

  private static final String GOBLIN = "Goblin";

  /**
   * This test verifies that when the wizard casts spells on the goblin. The wizard keeps track of
   * the previous spells cast, so it is easy to undo them. In addition, it also verifies that the
   * wizard keeps track of the spells undone, so they can be redone.
   */
  @Test
  public void testCommand() {

    Wizard wizard = new Wizard();
    Goblin goblin = new Goblin();

    wizard.castSpell(new ShrinkSpell(), goblin);
    verifyGoblin(goblin, GOBLIN, Size.SMALL, Visibility.VISIBLE);

    wizard.castSpell(new InvisibilitySpell(), goblin);
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
   * @param goblin a goblin object whose state is to be verified against other parameters
   * @param expectedName expectedName of the goblin
   * @param expectedSize expected size of the goblin
   * @param expectedVisibilty exepcted visibility of the goblin
   */
  private void verifyGoblin(Goblin goblin, String expectedName, Size expectedSize,
      Visibility expectedVisibilty) {
    assertEquals("Goblin's name must be same as expectedName", expectedName, goblin.toString());
    assertEquals("Goblin's size must be same as expectedSize", expectedSize, goblin.getSize());
    assertEquals("Goblin's visibility must be same as expectedVisibility", expectedVisibilty,
        goblin.getVisibility());
  }
}
