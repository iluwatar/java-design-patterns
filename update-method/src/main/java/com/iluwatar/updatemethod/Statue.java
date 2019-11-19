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

package com.iluwatar.updatemethod;

/**
 * Statues shoot lightning at regular intervals.
 */
public class Statue extends Entity {

  protected int frames;

  protected int delay;

  /**
   * Constructor of Statue.
   *
   * @param id id of statue
   */
  public Statue(int id) {
    super(id);
    this.frames = 0;
    this.delay = 0;
  }

  /**
   * Constructor of Statue.
   *
   * @param id id of statue
   * @param delay the number of frames between two lightning
   */
  public Statue(int id, int delay) {
    super(id);
    this.frames = 0;
    this.delay = delay;
  }

  @Override
  public void update() {
    if (++ frames == delay) {
      shootLightning();
      frames = 0;
    }
  }

  private void shootLightning() {
    logger.info("Statue " + id + " shoots lightning!");
  }
}
