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

package com.iluwatar.factorykit;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

/**
 * Factory kit is a creational pattern that defines a factory of immutable content with separated
 * builder and factory interfaces to deal with the problem of creating one of the objects specified
 * directly in the factory kit instance.
 * factory-kit是一种创建型模式，它使用分离的生成器和工厂接口定义了不可变内容的工厂，以处理创建直接在factory-kit实例中指定的对象的问题。
 *
 * <p>In the given example {@link WeaponFactory} represents the factory kit, that contains four
 * {@link Builder}s for creating new objects of the classes implementing {@link Weapon} interface.
 * 在给出的示例中，{@link WeaponFactory}表示工厂工具包，它包含四个{@link Builder}，用于创建实现{@link Weapon}接口的类的新对象。
 *
 * <p>Each of them can be called with {@link WeaponFactory#create(WeaponType)} method, with
 * an input representing an instance of {@link WeaponType} that needs to be mapped explicitly with
 * desired class type in the factory instance.
 * 每一个都可以用{@link WeaponFactory#create(WeaponType)}方法调用，输入表示一个{@link WeaponType}实例，需要在工厂实例中显式地映射所需的类类型。
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var factory = WeaponFactory.factory(builder -> {
      builder.add(WeaponType.SWORD, Sword::new);
      builder.add(WeaponType.AXE, Axe::new);
      builder.add(WeaponType.SPEAR, Spear::new);
      builder.add(WeaponType.BOW, Bow::new);
    });
    var list = new ArrayList<Weapon>();
    list.add(factory.create(WeaponType.AXE));
    list.add(factory.create(WeaponType.SPEAR));
    list.add(factory.create(WeaponType.SWORD));
    list.add(factory.create(WeaponType.BOW));
    list.stream().forEach(weapon -> LOGGER.info("{}", weapon.toString()));
  }
}
