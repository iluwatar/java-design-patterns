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

package com.iluwatar.command;

/**
 * The Command pattern is a behavioral design pattern in which an object is used to encapsulate all
 * information needed to perform an action or trigger an event at a later time. This information
 * includes the method name, the object that owns the method, and values for the method parameters.
 * Command模式是一种行为设计模式，在这种模式中，一个对象被用来封装执行一个动作或在以后触发一个事件所需的所有信息。该信息包括方法名、拥有该方法的对象和方法参数的值。
 *
 * <p>Four terms always associated with the command pattern are command, receiver, invoker and
 * client. A command object (spell) knows about the receiver (target) and invokes a method of the
 * receiver. An invoker object (wizard) receives a reference to the command to be executed and
 * optionally does bookkeeping about the command execution. The invoker does not know anything
 * about how the command is executed. The client decides which commands to execute at which
 * points. To execute a command, it passes a reference of the function to the invoker object.
 * 与命令模式相关联的四个术语是command、receiver、invoker和client。命令对象(spell)知道接收方(target)并调用接收方的方法。
 * 调用者对象(wizard)接收对要执行的命令的引用，并可选地记录有关命令执行的记录。调用者不知道命令是如何执行的。客户端决定在哪个点执行哪个命令。为了执行命令，它将函数的引用传递给调用者对象。
 *
 * <p>In other words, in this example the wizard casts spells on the goblin. The wizard keeps track
 * of the previous spells cast, so it is easy to undo them. In addition, the wizard keeps track of
 * the spells undone, so they can be redone.
 * 换句话说，在这个例子中，巫师对地精施法。巫师会跟踪之前施放的咒语，所以很容易解除它们。此外，巫师会跟踪那些未被解开的咒语，这样它们就可以被重做。
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var wizard = new Wizard();
    var goblin = new Goblin();

    goblin.printStatus();

    wizard.castSpell(goblin::changeSize);
    goblin.printStatus();

    wizard.castSpell(goblin::changeVisibility);
    goblin.printStatus();

    wizard.undoLastSpell();
    goblin.printStatus();

    wizard.undoLastSpell();
    goblin.printStatus();

    wizard.redoLastSpell();
    goblin.printStatus();

    wizard.redoLastSpell();
    goblin.printStatus();
  }
}
