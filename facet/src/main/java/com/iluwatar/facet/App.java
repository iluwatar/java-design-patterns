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
package com.iluwatar.facet;

import com.iluwatar.facet.dragon.*;

/**
 * A facet is a class functioning as an interface to something else which provides less functionality.
 * It is a wrapper often used for security purposes, so that the Principle of Least Authority is
 * observed. It is technically a special case of proxy.
 *
 * <p>The Facet design pattern allows you to provide an interface to other objects by creating a
 * wrapper class as the facet. The wrapper class, which is the facet, must only provide access to
 * certain functions and under certain parameters, but never add functionality.
 *
 * <p>In this example the facet ({@link DragonFacet}) controls access to the actual object (
 * {@link Dragon}).
 */
public class App {

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    var facet = new DragonFacet(new Dragon(100));
    var sirJim = new Knight("Sir Jim", Attack.WATER_PISTOL, facet);
    sirJim.attackDragon();
    var sirLuke = new Knight("Sir Luke", Attack.FLAME_THROWER, facet);
    sirLuke.attackDragon();
  }
}
