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
package com.iluwatar.component;

import java.awt.event.KeyEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * The component design pattern is a common game design structure. This pattern is often
 * used to reduce duplication of code as well as to improve maintainability.
 * In this implementation, component design pattern has been used to provide two game
 * objects with varying component interfaces (features). As opposed to copying and
 * pasting same code for the two game objects, the component interfaces allow game
 * objects to inherit these components from the component classes.
 *
 * <p>The implementation has decoupled graphic, physics and input components from
 * the player and NPC objects. As a result, it avoids the creation of monolithic java classes.
 *
 * <p>The below example in this App class demonstrates the use of the component interfaces
 * for separate objects (player & NPC) and updating of these components as per the
 * implementations in GameObject class and the component classes.
 */
@Slf4j
public final class App {
  /**
   * Program entry point.
   *
   * @param args args command line args.
   */
  public static void main(String[] args) {
    final var player = GameObject.createPlayer();
    final var npc = GameObject.createNpc();


    LOGGER.info("Player Update:");
    player.update(KeyEvent.KEY_LOCATION_LEFT);
    LOGGER.info("NPC Update:");
    npc.demoUpdate();
  }
}
