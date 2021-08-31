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

package com.iluwatar.composite;

import lombok.extern.slf4j.Slf4j;

/**
 * The Composite pattern is a partitioning design pattern. The Composite pattern describes that a
 * group of objects is to be treated in the same way as a single instance of an object. The intent
 * of a composite is to "compose" objects into tree structures to represent part-whole hierarchies.
 * Implementing the Composite pattern lets clients treat individual objects and compositions
 * uniformly.
 * 组合模式是一种分区设计模式。Composite模式描述了将一组对象作为对象的单个实例进行处理的方式。组合的目的是将对象“组合”成树结构，以表示部分-整体层次结构。
 * 实现Composite模式让客户端能够统一对待单个对象和组合。
 *
 * <p>In this example we have sentences composed of words composed of letters. All of the objects
 * can be treated through the same interface ({@link LetterComposite}).
 * 在这个例子中，我们有由字母组成的单词组成的句子。所有的对象都可以通过相同的接口来处理({@link LetterComposite})。
 *
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    var messenger = new Messenger();

    LOGGER.info("Message from the orcs: ");
    messenger.messageFromOrcs().print();

    LOGGER.info("Message from the elves: ");
    messenger.messageFromElves().print();
  }
}
