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

package com.iluwatar.flyweight;

/**
 * Flyweight pattern is useful when the program needs a huge amount of objects. It provides means to
 * decrease resource usage by sharing object instances.
 * 享元模式在程序需要大量对象时非常有用。它提供了通过共享对象实例来减少资源使用的方法。
 *
 * <p>In this example {@link AlchemistShop} has great amount of potions on its shelves. To fill the
 * shelves {@link AlchemistShop} uses {@link PotionFactory} (which represents the Flyweight in this
 * example). Internally {@link PotionFactory} holds a map of the potions and lazily creates new ones
 * when requested.
 * 在这个例子中{@link AlchemistShop}的货架上有大量的药水。为了填充货架，{@link AlchemistShop}使用{@link PotionFactory}(它代表了本例中的Flyweight)。
 * 在内部{@link PotionFactory}持有药水地图，并在请求时惰性地创建新药水。
 *
 * <p>To enable safe sharing, between clients and threads, Flyweight objects must be immutable.
 * Flyweight objects are by definition value objects.
 * 为了在客户端和线程之间实现安全共享，Flyweight对象必须是不可变的。按定义，Flyweight对象是值对象。
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    // create the alchemist shop with the potions
    var alchemistShop = new AlchemistShop();
    // a brave visitor enters the alchemist shop and drinks all the potions
    alchemistShop.drinkPotions();
  }
}
