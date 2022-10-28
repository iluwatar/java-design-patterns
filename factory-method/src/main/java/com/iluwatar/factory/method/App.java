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
 * The Factory Method is a creational design pattern which uses factory methods to deal with the
 * problem of creating objects without specifying the exact class of object that will be created.
 * This is done by creating objects via calling a factory method either specified in an interface
 * and implemented by child classes, or implemented in a base class and optionally overridden by
 * derived classes—rather than by calling a constructor.
 *
 * <p>In this Factory Method example we have an interface ({@link Blacksmith}) with a method for
 * creating objects ({@link Blacksmith#manufactureWeapon}). The concrete subclasses (
 * {@link OrcBlacksmith}, {@link ElfBlacksmith}) then override the method to produce objects of
 * their liking.
 *
 */
@Slf4j
public class App {

  private final Blacksmith blacksmith;

  /**
   * Creates an instance of <code>App</code> which will use <code>blacksmith</code> to manufacture 
   * the weapons for war.
   * <code>App</code> is unaware which concrete implementation of {@link Blacksmith} it is using.
   * The decision of which blacksmith implementation to use may depend on configuration, or
   * the type of rival in war.
   * @param blacksmith a non-null implementation of blacksmith
   */
  public App(Blacksmith blacksmith) {
    this.blacksmith = blacksmith;
  }

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    // Lets go to war with Orc weapons
    var app = new App(new OrcBlacksmith());
    app.manufactureWeapons();

    // Lets go to war with Elf weapons
    app = new App(new ElfBlacksmith());
    app.manufactureWeapons();
  }

  private void manufactureWeapons() {
    var weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
    LOGGER.info(weapon.toString());
    weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
    LOGGER.info(weapon.toString());
  }
}
