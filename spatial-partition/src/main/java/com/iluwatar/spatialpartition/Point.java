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

package com.iluwatar.spatialpartition;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The abstract Point class which will be extended by any object in the field whose location has to
 * be kept track of. Defined by x,y coordinates and an id for easy hashing into hashtable.
 *
 * @param <T> T will be type subclass
 */

public abstract class Point<T> {

  public int coordinateX;
  public int coordinateY;
  public final int id;

  Point(int x, int y, int id) {
    this.coordinateX = x;
    this.coordinateY = y;
    this.id = id;
  }

  /**
   * defines how the object moves.
   */
  abstract void move();

  /**
   * defines conditions for interacting with an object obj.
   *
   * @param obj is another object on field which also extends Point
   * @return whether the object can interact with the other or not
   */
  abstract boolean touches(T obj);

  /**
   * handling interactions/collisions with other objects.
   *
   * @param pointsToCheck contains the objects which need to be checked
   * @param allPoints     contains hashtable of all points on field at this time
   */
  abstract void handleCollision(ArrayList<Point> pointsToCheck, Hashtable<Integer, T> allPoints);
}
