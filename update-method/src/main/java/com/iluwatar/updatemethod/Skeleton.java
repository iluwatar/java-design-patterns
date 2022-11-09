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
package com.iluwatar.updatemethod;

/**
 * Skeletons are always patrolling on the game map. Initially all the skeletons
 * patrolling to the right, and after them reach the bounding, it will start
 * patrolling to the left. For each frame, one skeleton will move 1 position
 * step.
 */
public class Skeleton extends Entity {

  private static final int PATROLLING_LEFT_BOUNDING = 0;

  private static final int PATROLLING_RIGHT_BOUNDING = 100;

  protected boolean patrollingLeft;

  /**
   * Constructor of Skeleton.
   *
   * @param id id of skeleton
   */
  public Skeleton(int id) {
    super(id);
    patrollingLeft = false;
  }

  /**
   * Constructor of Skeleton.
   *
   * @param id id of skeleton
   * @param position position of skeleton
   */
  public Skeleton(int id, int position) {
    super(id);
    this.position = position;
    patrollingLeft = false;
  }

  @Override
  public void update() {
    if (patrollingLeft) {
      position -= 1;
      if (position == PATROLLING_LEFT_BOUNDING) {
        patrollingLeft = false;
      }
    } else {
      position += 1;
      if (position == PATROLLING_RIGHT_BOUNDING) {
        patrollingLeft = true;
      }
    }
    logger.info("Skeleton {} is on position {}.", id, position);
  }
}

