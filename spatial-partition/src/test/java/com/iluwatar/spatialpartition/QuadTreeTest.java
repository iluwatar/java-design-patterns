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

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;


import org.junit.jupiter.api.Test;

/**
 * Testing QuadTree class.
 */

class QuadTreeTest {

  @Test
  void queryTest() {
    ArrayList<Point> points = new ArrayList<Point>();
    Random rand = new Random();
    for (int i = 0; i < 20; i++) {
      Bubble p = new Bubble(rand.nextInt(300), rand.nextInt(300), i, rand.nextInt(2) + 1);
      points.add(p);
    }
    Rect field = new Rect(150,150,300,300); //size of field
    Rect queryRange = new Rect(70,130,100,100); //result = all points lying in this rectangle
    //points found in the query range using quadtree and normal method is same
    assertTrue(QuadTreeTest.quadTreeTest(points, field, queryRange).equals(QuadTreeTest.verify(points, queryRange)));
  }

  static Hashtable<Integer, Point> quadTreeTest(ArrayList<Point> points, Rect field, Rect queryRange) {
    //creating quadtree and inserting all points
    QuadTree qTree = new QuadTree(queryRange, 4);
    for (int i = 0; i < points.size(); i++) {
      qTree.insert(points.get(i));
    }

    ArrayList<Point> queryResult = qTree.query(field, new ArrayList<Point>());
    Hashtable<Integer, Point> result = new Hashtable<Integer, Point>();
    for (int i = 0; i < queryResult.size(); i++) {
      Point p = queryResult.get(i);
      result.put(p.id, p);
    }
    return result;
  }

  static Hashtable<Integer, Point> verify(ArrayList<Point> points, Rect queryRange) {
    Hashtable<Integer, Point> result = new Hashtable<Integer, Point>();
    for (int i = 0; i < points.size(); i++) {
      if (queryRange.contains(points.get(i))) {
        result.put(points.get(i).id, points.get(i));
      }
    }
    return result;
  }
}
