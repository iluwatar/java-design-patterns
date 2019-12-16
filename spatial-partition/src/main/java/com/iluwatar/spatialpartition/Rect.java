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

/**
 * The Rect class helps in defining the boundary of the quadtree and is also used to define the
 * range within which objects need to be found in our example.
 */

public class Rect {
  double coordinateX;
  double coordinateY;
  double width;
  double height;

  //(x,y) - centre of rectangle

  Rect(double x, double y, double width, double height) {
    this.coordinateX = x;
    this.coordinateY = y;
    this.width = width;
    this.height = height;
  }

  boolean contains(Point p) {
    return p.coordinateX >= this.coordinateX - this.width / 2
        && p.coordinateX <= this.coordinateX + this.width / 2
        && p.coordinateY >= this.coordinateY - this.height / 2
        && p.coordinateY <= this.coordinateY + this.height / 2;
  }

  boolean intersects(Rect other) {
    return !(this.coordinateX + this.width / 2 <= other.coordinateX - other.width / 2
        || this.coordinateX - this.width / 2 >= other.coordinateX + other.width / 2
        || this.coordinateY + this.height / 2 <= other.coordinateY - other.height / 2
        || this.coordinateY - this.height / 2 >= other.coordinateY + other.height / 2);
  }
}

