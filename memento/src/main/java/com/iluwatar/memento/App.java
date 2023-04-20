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
package com.iluwatar.memento;

import java.util.Stack;
import lombok.extern.slf4j.Slf4j;

/**
 * The Memento pattern is a software design pattern that provides the ability to restore an object
 * to its previous state (undo via rollback).
 * Memento模式是一种软件设计模式，它能够将对象恢复到以前的状态(通过回滚撤消)。
 *
 * <p>The Memento pattern is implemented with three objects: the originator, a caretaker and a
 * memento. The originator is some object that has an internal state. The caretaker is going to do
 * something to the originator, but wants to be able to undo the change. The caretaker first asks
 * the originator for a memento object. Then it does whatever operation (or sequence of operations)
 * it was going to do. To roll back to the state before the operations, it returns the memento
 * object to the originator. The memento object itself is an opaque object (one which the caretaker
 * cannot, or should not, change). When using this pattern, care should be taken if the originator
 * may change other objects or resources - the memento pattern operates on a single object.
 * Memento模式由三个对象实现:发起者、管理员和备忘录。发起者是某个具有内部状态的对象。管理员会对发起者做一些事情，但希望能够撤销更改。
 * 管理员首先向发起者索要一件备忘录。然后它执行它要执行的任何操作(或操作序列)。为了回滚到操作之前的状态，它将memento对象返回给发起者。
 * memento对象本身是一个不透明的对象(管理员不能或不应该更改的对象)。当使用此模式时，如果发起者可能会更改其他对象或资源，则应谨慎处理——备忘录模式操作于单个对象。
 *
 * <p>In this example the object ({@link Star}) gives out a "memento" ({@link StarMemento}) that
 * contains the state of the object. Later on the memento can be set back to the object restoring
 * the state.
 * 在这个例子中，对象({@link Star})给出了一个包含对象状态的“memento”({@link StarMemento})。稍后，可以将备忘录设置回恢复状态的对象。
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    var states = new Stack<StarMemento>();

    var star = new Star(StarType.SUN, 10000000, 500000);
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    states.add(star.getMemento());
    star.timePasses();
    LOGGER.info(star.toString());
    while (states.size() > 0) {
      star.setMemento(states.pop());
      LOGGER.info(star.toString());
    }
  }
}
