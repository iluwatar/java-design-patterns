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
package com.iluwatar.gameloop;

/**
 * Frame-based game loop is the easiest implementation. The loop always keeps spinning
 * for the following three processes: processInput, update and render. The problem with
 * it is you have no control over how fast the game runs. On a fast machine, that loop
 * will spin so fast users won’t be able to see what’s going on. On a slow machine, the
 * game will crawl. If you have a part of the game that’s content-heavy or does more AI
 * or physics, the game will actually play slower there.
 */
public class FrameBasedGameLoop extends GameLoop {

  @Override
  protected void processGameLoop() {
    while (isGameRunning()) {
      processInput();
      update();
      render();
    }
  }

  /**
   * Each time when update() is invoked, a new frame is created, and the bullet will be
   * moved 0.5f away from the current position.
   */
  protected void update() {
    controller.moveBullet(0.5f);
  }

}
