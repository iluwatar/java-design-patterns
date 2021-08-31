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

package com.iluwatar.mediator;

/**
 * The Mediator pattern defines an object that encapsulates how a set of objects interact. This
 * pattern is considered to be a behavioral pattern due to the way it can alter the program's
 * running behavior.
 * Mediator模式定义了一个对象，该对象封装了一组对象如何交互。这种模式被认为是一种行为模式，因为它可以改变程序的运行行为。
 *
 * <p>Usually a program is made up of a large number of classes. So the logic and computation is
 * distributed among these classes. However, as more classes are developed in a program, especially
 * during maintenance and/or refactoring, the problem of communication between these classes may
 * become more complex. This makes the program harder to read and maintain. Furthermore, it can
 * become difficult to change the program, since any change may affect code in several other
 * classes.
 * 通常一个程序是由大量的类组成的。所以逻辑和计算分布在这些类中。然而，随着程序中开发的类越来越多，特别是在维护和/或重构期间，这些类之间的通信问题可能会变得更加复杂。
 * 这使得程序更难阅读和维护。此外，更改程序会变得很困难，因为任何更改都可能影响其他几个类中的代码。
 *
 * <p>With the Mediator pattern, communication between objects is encapsulated with a mediator
 * object. Objects no longer communicate directly with each other, but instead communicate through
 * the mediator. This reduces the dependencies between communicating objects, thereby lowering the
 * coupling.
 * 使用中介模式，对象之间的通信是用中介对象封装的。对象之间不再直接通信，而是通过中介进行通信。这减少了通信对象之间的依赖关系，从而降低了耦合。
 *
 * <p>In this example the mediator encapsulates how a set of objects ({@link PartyMember})
 * interact. Instead of referring to each other directly they use the mediator ({@link Party})
 * interface.
 * 在这个例子中，中介封装了一组对象({@link PartyMember})的交互方式。它们不是直接引用彼此，而是使用中介({@link Party})接口。
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    // create party and members
    Party party = new PartyImpl();
    var hobbit = new Hobbit();
    var wizard = new Wizard();
    var rogue = new Rogue();
    var hunter = new Hunter();

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
