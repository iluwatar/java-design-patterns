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

package com.iluwatar.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * The Decorator pattern is a more flexible alternative to subclassing. The Decorator class
 * implements the same interface as the target and uses composition to "decorate" calls to the
 * target. Using the Decorator pattern it is possible to change the behavior of the class during
 * runtime.
 * Decorator模式是子类化的一种更灵活的替代方法。Decorator类实现与目标相同的接口，并使用组合来“装饰”对目标的调用。使用Decorator模式，可以在运行时更改类的行为。
 *
 * <p>In this example we show how the simple {@link SimpleTroll} first attacks and then flees the
 * battle. Then we decorate the {@link SimpleTroll} with a {@link ClubbedTroll} and perform the
 * attack again. You can see how the behavior changes after the decoration.
 * 在这个例子中，我们展示了简单的{@link SimpleTroll}如何首先攻击，然后逃离战斗。然后我们用{@link ClubbedTroll}装饰{@link SimpleTroll}，并再次执行攻击。你可以看到装饰后行为的变化。
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    // simple troll
    LOGGER.info("A simple looking troll approaches.");
    var troll = new SimpleTroll();
    troll.attack();
    troll.fleeBattle();
    LOGGER.info("Simple troll power: {}.\n", troll.getAttackPower());

    // change the behavior of the simple troll by adding a decorator
    LOGGER.info("A troll with huge club surprises you.");
    var clubbedTroll = new ClubbedTroll(troll);
    clubbedTroll.attack();
    clubbedTroll.fleeBattle();
    LOGGER.info("Clubbed troll power: {}.\n", clubbedTroll.getAttackPower());
  }
}
