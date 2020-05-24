/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.component;

import java.util.ArrayList;
/**
 * Object-oriented to a certain extent can solve many problems of code reuse and data reuse,
 * but it also has great defects:
 * 1.the coupling of data organization is very strong.
 * 2.Interface logic is difficult to reuse and hot plug.
 *
 * The component pattern solves the defects of object orientation and process orientation and
 * is widely used in game clients
 *
 * A component is a part of one object. We can consider that a object contains multiple
 * components, in another way, multiple components can construct a object.
 * Here is a demo using component pattern to solve a game-like problem.
 * A person named Bjorn who has three components: input;physics;graphics
 * These three component with a common game object can construct our protagonist:Bjorn, also can
 * construct other objects like dog or cat if you want to write a real game.
 */

public class App {
  /**
   * Launcher for this demo design pattern
   */
  public static void main(String[] args) {
    ArrayList<Component> arrayList = new ArrayList<>();
    arrayList.add(new BjornInputComponent());
    arrayList.add(new BjornPhysicsComponent());
    arrayList.add(new BjornGraphicsComponent());
    GameObject gameObject = new GameObject(arrayList);
    gameObject.update();
  }
}
