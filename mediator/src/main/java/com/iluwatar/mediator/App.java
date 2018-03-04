/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.mediator;

/**
 * 
 * The Mediator pattern defines an object that encapsulates how a set of objects interact. This
 * pattern is considered to be a behavioral pattern due to the way it can alter the program's
 * running behavior.
 * <p>
 * Usually a program is made up of a large number of classes. So the logic and computation is
 * distributed among these classes. However, as more classes are developed in a program, especially
 * during maintenance and/or refactoring, the problem of communication between these classes may
 * become more complex. This makes the program harder to read and maintain. Furthermore, it can
 * become difficult to change the program, since any change may affect code in several other
 * classes.
 * <p>
 * With the Mediator pattern, communication between objects is encapsulated with a mediator object.
 * Objects no longer communicate directly with each other, but instead communicate through the
 * mediator. This reduces the dependencies between communicating objects, thereby lowering the
 * coupling.
 * <p>
 * In this example the mediator encapsulates how a set of objects ({@link PartyMember}) interact.
 * Instead of referring to each other directly they use the mediator ({@link Party}) interface.
 * 
 */
public class App {

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {

    // create party and members
    Party party = new PartyImpl();
    Hobbit hobbit = new Hobbit();
    Wizard wizard = new Wizard();
    Rogue rogue = new Rogue();
    Hunter hunter = new Hunter();

    // add party members
    party.addMember(hobbit);
    party.addMember(wizard);
    party.addMember(rogue);
    party.addMember(hunter);

    // perform actions -> the other party members
    // are notified by the party
    hobbit.act(Action.ENEMY);
    wizard.act(Action.TALE);
    rogue.act(Action.GOLD);
    hunter.act(Action.HUNT);
  }
}
