/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.tolerantreader;

/**
 * 
 * RainbowFishV2 is the evolved schema
 *
 */
public class RainbowFishV2 extends RainbowFish {

  private static final long serialVersionUID = 1L;

  private boolean sleeping;
  private boolean hungry;
  private boolean angry;

  public RainbowFishV2(String name, int age, int lengthMeters, int weightTons) {
    super(name, age, lengthMeters, weightTons);
  }

  /**
   * Constructor
   */
  public RainbowFishV2(String name, int age, int lengthMeters, int weightTons, boolean sleeping,
      boolean hungry, boolean angry) {
    this(name, age, lengthMeters, weightTons);
    this.sleeping = sleeping;
    this.hungry = hungry;
    this.angry = angry;
  }

  public boolean getSleeping() {
    return sleeping;
  }

  public boolean getHungry() {
    return hungry;
  }

  public boolean getAngry() {
    return angry;
  }
}
