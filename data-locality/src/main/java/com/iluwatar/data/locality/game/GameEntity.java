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

package com.iluwatar.data.locality.game;

import com.iluwatar.data.locality.game.component.manager.AiComponentManager;
import com.iluwatar.data.locality.game.component.manager.PhysicsComponentManager;
import com.iluwatar.data.locality.game.component.manager.RenderComponentManager;
import lombok.extern.slf4j.Slf4j;

/**
 * The game Entity maintains a big array of pointers . Each spin of the game loop, we need to run
 * the following:
 *
 * <p>Update the AI components.
 *
 * <p>Update the physics components for them.
 *
 * <p>Render them using their render components.
 */
@Slf4j
public class GameEntity {

  private final AiComponentManager aiComponentManager;
  private final PhysicsComponentManager physicsComponentManager;
  private final RenderComponentManager renderComponentManager;

  /**
   * Init components.
   */
  public GameEntity(int numEntities) {
    LOGGER.info("Init Game with #Entity : {}", numEntities);
    aiComponentManager = new AiComponentManager(numEntities);
    physicsComponentManager = new PhysicsComponentManager(numEntities);
    renderComponentManager = new RenderComponentManager(numEntities);
  }

  /**
   * start all component.
   */
  public void start() {
    LOGGER.info("Start Game");
    aiComponentManager.start();
    physicsComponentManager.start();
    renderComponentManager.start();
  }

  /**
   * update all component.
   */
  public void update() {
    LOGGER.info("Update Game Component");
    // Process AI.
    aiComponentManager.update();

    // update physics.
    physicsComponentManager.update();

    // Draw to screen.
    renderComponentManager.render();
  }

}
