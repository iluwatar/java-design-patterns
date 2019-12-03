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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The game world class. Maintain all the objects existed in the game frames.
 */
public class World {

  private static final Logger LOGGER = LoggerFactory.getLogger(World.class);

  protected List<Entity> entities;

  protected volatile boolean isRunning;

  public World() {
    entities = new ArrayList<>();
    isRunning = false;
  }

  /**
   * Main game loop. This loop will always run until the game is over. For
   * each loop it will process user input, update internal status, and render
   * the next frames. For more detail please refer to the game-loop pattern.
   */
  private void gameLoop() {
    while (isRunning) {
      processInput();
      update();
      render();
    }
  }

  /**
   * Handle any user input that has happened since the last call. In order to
   * simulate the situation in real-life game, here we add a random time lag.
   * The time lag ranges from 50 ms to 250 ms.
   */
  private void processInput() {
    try {
      int lag = new Random().nextInt(200) + 50;
      Thread.sleep(lag);
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * Update internal status. The update method pattern invoke udpate method for
   * each entity in the game.
   */
  private void update() {
    for (var entity : entities) {
      entity.update();
    }
  }

  /**
   * Render the next frame. Here we do nothing since it is not related to the
   * pattern.
   */
  private void render() {}

  /**
   * Run game loop.
   */
  public void run() {
    LOGGER.info("Start game.");
    isRunning = true;
    var thread = new Thread(this::gameLoop);
    thread.start();
  }

  /**
   * Stop game loop.
   */
  public void stop() {
    LOGGER.info("Stop game.");
    isRunning = false;
  }

  public void addEntity(Entity entity) {
    entities.add(entity);
  }

}
