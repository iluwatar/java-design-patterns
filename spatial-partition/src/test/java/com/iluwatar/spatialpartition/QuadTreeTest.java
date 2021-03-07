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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * Testing QuadTree class.
 */

class QuadTreeTest {

  @Test
  void queryTest() {
    var points = new ArrayList<Point>();
    var rand = new Random();
    for (int i = 0; i < 20; i++) {
      var p = new Bubble(rand.nextInt(300), rand.nextInt(300), i, rand.nextInt(2) + 1);
      points.add(p);
    }
    var field = new Rect(150, 150, 300, 300); //size of field
    var queryRange = new Rect(70, 130, 100, 100); //result = all points lying in this rectangle
    //points found in the query range using quadtree and normal method is same
    var points1 = QuadTreeTest.quadTreeTest(points, field, queryRange);
    var points2 = QuadTreeTest.verify(points, queryRange);
    assertEquals(points1, points2);
  }

  static Hashtable<Integer, Point> quadTreeTest(Collection<Point> points, Rect field, Rect queryRange) {
    //creating quadtree and inserting all points
    var qTree = new QuadTree(queryRange, 4);
    points.forEach(qTree::insert);

    return qTree
        .query(field, new ArrayList<>())
        .stream()
        .collect(Collectors.toMap(p -> p.id, p -> p, (a, b) -> b, Hashtable::new));
  }

  static Hashtable<Integer, Point> verify(Collection<Point> points, Rect queryRange) {
    return points.stream()
        .filter(queryRange::contains)
        .collect(Collectors.toMap(point -> point.id, point -> point, (a, b) -> b, Hashtable::new));
  }
}
