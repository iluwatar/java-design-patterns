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

package com.iluwatar.spatialpartition;

import java.util.Collection;
import java.util.Hashtable;

/**
 * The quadtree data structure is being used to keep track of the objects' locations. It has the
 * insert(Point) and query(range) methods to insert a new object and find the objects within a
 * certain (rectangular) range respectively.
 */

public class QuadTree {
  Rect boundary;
  int capacity;
  boolean divided;
  Hashtable<Integer, Point> points;
  QuadTree northwest;
  QuadTree northeast;
  QuadTree southwest;
  QuadTree southeast;

  QuadTree(Rect boundary, int capacity) {
    this.boundary = boundary;
    this.capacity = capacity;
    this.divided = false;
    this.points = new Hashtable<>();
    this.northwest = null;
    this.northeast = null;
    this.southwest = null;
    this.southeast = null;
  }

  void insert(Point p) {
    if (this.boundary.contains(p)) {
      if (this.points.size() < this.capacity) {
        points.put(p.id, p);
      } else {
        if (!this.divided) {
          this.divide();
        }
        if (this.northwest.boundary.contains(p)) {
          this.northwest.insert(p);
        } else if (this.northeast.boundary.contains(p)) {
          this.northeast.insert(p);
        } else if (this.southwest.boundary.contains(p)) {
          this.southwest.insert(p);
        } else if (this.southeast.boundary.contains(p)) {
          this.southeast.insert(p);
        }
      }
    }
  }

  void divide() {
    var x = this.boundary.coordinateX;
    var y = this.boundary.coordinateY;
    var width = this.boundary.width;
    var height = this.boundary.height;
    var nw = new Rect(x - width / 4, y + height / 4, width / 2, height / 2);
    this.northwest = new QuadTree(nw, this.capacity);
    var ne = new Rect(x + width / 4, y + height / 4, width / 2, height / 2);
    this.northeast = new QuadTree(ne, this.capacity);
    var sw = new Rect(x - width / 4, y - height / 4, width / 2, height / 2);
    this.southwest = new QuadTree(sw, this.capacity);
    var se = new Rect(x + width / 4, y - height / 4, width / 2, height / 2);
    this.southeast = new QuadTree(se, this.capacity);
    this.divided = true;
  }

  Collection<Point> query(Rect r, Collection<Point> relevantPoints) {
    //could also be a circle instead of a rectangle
    if (this.boundary.intersects(r)) {
      this.points
          .values()
          .stream()
          .filter(r::contains)
          .forEach(relevantPoints::add);
      if (this.divided) {
        this.northwest.query(r, relevantPoints);
        this.northeast.query(r, relevantPoints);
        this.southwest.query(r, relevantPoints);
        this.southeast.query(r, relevantPoints);
      }
    }
    return relevantPoints;
  }
}
