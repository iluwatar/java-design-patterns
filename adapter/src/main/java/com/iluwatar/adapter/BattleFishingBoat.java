/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.adapter;

/**
 *
 * Adapter class. Adapts the interface of the device ({@link FishingBoat}) into {@link BattleShip}
 * interface expected by the client ({@link Captain}). <br>
 * In this case we added a new function fire to suit the interface. We are reusing the
 * {@link FishingBoat} without changing itself. The Adapter class can just map the functions of the
 * Adaptee or add, delete features of the Adaptee.
 *
 */
public class BattleFishingBoat implements BattleShip {

  private FishingBoat boat;

  public BattleFishingBoat() {
    boat = new FishingBoat();
  }

  @Override
  public void fire() {
    System.out.println("fire!");
  }

  @Override
  public void move() {
    boat.sail();
  }
}
