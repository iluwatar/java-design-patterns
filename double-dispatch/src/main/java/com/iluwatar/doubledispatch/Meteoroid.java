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

package com.iluwatar.doubledispatch;

import com.iluwatar.doubledispatch.constants.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Meteoroid game object.
 */
public class Meteoroid extends GameObject {

  private static final Logger LOGGER = LoggerFactory.getLogger(Meteoroid.class);

  public Meteoroid(int left, int top, int right, int bottom) {
    super(left, top, right, bottom);
  }

  @Override
  public void collision(GameObject gameObject) {
    gameObject.collisionResolve(this);
  }

  @Override
  public void collisionResolve(FlamingAsteroid asteroid) {
    LOGGER.info(AppConstants.HITS, asteroid.getClass().getSimpleName(), this.getClass()
        .getSimpleName());
  }

  @Override
  public void collisionResolve(Meteoroid meteoroid) {
    LOGGER.info(AppConstants.HITS, meteoroid.getClass().getSimpleName(), this.getClass()
        .getSimpleName());
  }

  @Override
  public void collisionResolve(SpaceStationMir mir) {
    LOGGER.info(AppConstants.HITS, mir.getClass().getSimpleName(), this.getClass().getSimpleName());
  }

  @Override
  public void collisionResolve(SpaceStationIss iss) {
    LOGGER.info(AppConstants.HITS, iss.getClass().getSimpleName(), this.getClass().getSimpleName());
  }
}
