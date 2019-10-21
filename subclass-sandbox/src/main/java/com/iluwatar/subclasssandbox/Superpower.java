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

package com.iluwatar.subclasssandbox;

import org.slf4j.Logger;

/**
 * Superpower abstract class. In this class the basic operations of all types of
 * superpowers are provided as protected methods.
 */
public abstract class Superpower {

  protected Logger logger;

  /**
   * Subclass of superpower should implement this sandbox method by calling the
   * methods provided in this super class.
   */
  protected abstract void activate();

  /**
   * Move to (x, y, z).
   * @param x X coordinate.
   * @param y Y coordinate.
   * @param z Z coordinate.
   */
  protected void move(double x, double y, double z) {
    logger.info("Move to ( " + x + ", " + y + ", " + z + " )");
  }

  /**
   * Play sound effect for the superpower.
   * @param soundName Sound name.
   * @param volumn Value of volumn.
   */
  protected void playSound(String soundName, int volumn) {
    logger.info("Play " + soundName + " with volumn " + volumn);
  }

  /**
   * Spawn particles for the superpower.
   * @param particleType Particle type.
   * @param count Count of particles to be spawned.
   */
  protected void spawnParticles(String particleType, int count) {
    logger.info("Spawn " + count + " particle with type " + particleType);
  }
}
