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

package com.iluwatar.data.locality.game.component.manager;

import com.iluwatar.data.locality.game.component.AiComponent;
import com.iluwatar.data.locality.game.component.Component;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AI component manager for Game.
 */
public class AiComponentManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(AiComponentManager.class);

  private static final int MAX_ENTITIES = 10000;

  private final int numEntities;

  private static final Component[] AI_COMPONENTS = new AiComponent[MAX_ENTITIES];

  public AiComponentManager(int numEntities) {
    this.numEntities = numEntities;
  }

  /**
   * start AI component of Game.
   */
  public void start() {
    LOGGER.info("Start AI Game Component");
    IntStream.range(0, numEntities).forEach(i -> AI_COMPONENTS[i] = new AiComponent());
  }

  /**
   * Update AI component of Game.
   */
  public void update() {
    LOGGER.info("Update AI Game Component");
    IntStream.range(0, numEntities)
        .filter(i -> AI_COMPONENTS.length > i && AI_COMPONENTS[i] != null)
        .forEach(i -> AI_COMPONENTS[i].update());
  }
}
