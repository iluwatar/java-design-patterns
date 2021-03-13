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

package com.iluwatar.data.locality;

import com.iluwatar.data.locality.game.GameEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * Use the Data Locality pattern is when you have a performance problem. Take advantage of that to
 * improve performance by increasing data locality — keeping data in contiguous memory in the order
 * that you process it.
 *
 * <p>Example:  Game loop that processes a bunch of game entities. Those entities are decomposed
 * into different domains  — AI, physics, and rendering — using the Component pattern.
 */
@Slf4j
public class Application {

  private static final int NUM_ENTITIES = 5;

  /**
   * Start game loop with each component have NUM_ENTITIES instance.
   */
  public static void main(String[] args) {
    LOGGER.info("Start Game Application using Data-Locality pattern");
    var gameEntity = new GameEntity(NUM_ENTITIES);
    gameEntity.start();
    gameEntity.update();
  }
}
