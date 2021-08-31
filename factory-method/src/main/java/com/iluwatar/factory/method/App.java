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

package com.iluwatar.factory.method;

import lombok.extern.slf4j.Slf4j;

/**
 * The Factory Method is a creational design pattern that uses factory methods to deal with the
 * problem of creating objects without specifying the exact class of object that will be created.
 * This is done by creating objects via calling a factory method either specified in an interface
 * and implemented by child classes, or implemented in a base class and optionally overridden by
 * derived classes—rather than by calling a constructor.
 * 工厂方法是一个创建设计模式，它使用工厂方法来处理创建对象的问题，而不指定将要创建的对象的确切类。
 * 通过调用工厂方法来创建对象，工厂方法可以在接口中指定并由子类实现，也可以在基类中实现并可选地由派生类覆盖——而不是调用构造函数。
 *
 * <p>In this Factory Method example we have an interface ({@link Blacksmith}) with a method for
 * creating objects ({@link Blacksmith#manufactureWeapon}). The concrete subclasses (
 * {@link OrcBlacksmith}, {@link ElfBlacksmith}) then override the method to produce objects of
 * their liking.
 * 在这个工厂方法示例中，我们有一个接口({@link Blacksmith}) 和一个创建对象({@link Blacksmith#manufactureWeapon})的方法。然后具体的
 * 子类{@link OrcBlacksmith}, {@link ElfBlacksmith})重写该方法以生成自己喜欢的对象。
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   * @param args command line args
   */
  public static void main(String[] args) {

    Blacksmith blacksmith = new OrcBlacksmith();
    Weapon weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
    LOGGER.info("{} manufactured {}", blacksmith, weapon);
    weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
    LOGGER.info("{} manufactured {}", blacksmith, weapon);

    blacksmith = new ElfBlacksmith();
    weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
    LOGGER.info("{} manufactured {}", blacksmith, weapon);
    weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
    LOGGER.info("{} manufactured {}", blacksmith, weapon);
  }
}
