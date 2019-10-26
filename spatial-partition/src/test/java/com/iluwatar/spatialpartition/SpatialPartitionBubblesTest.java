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
import java.util.Hashtable;
import org.junit.jupiter.api.Test;

/**
 * Testing SpatialPartition_Bubbles class.
 */

class SpatialPartitionBubblesTest {

  @Test
  void handleCollisionsUsingQtTest() {
    Bubble b1 = new Bubble(10,10,1,3);
    Bubble b2 = new Bubble(5,5,2,1);
    Bubble b3 = new Bubble(9,9,3,1);
    Bubble b4 = new Bubble(8,8,4,2);
    Hashtable<Integer, Bubble> bubbles = new Hashtable<Integer, Bubble>();
    bubbles.put(1, b1); 
    bubbles.put(2, b2); 
    bubbles.put(3, b3); 
    bubbles.put(4, b4);
    Rect r = new Rect(10,10,20,20);
    QuadTree qt = new QuadTree(r,4);
    qt.insert(b1); 
    qt.insert(b2); 
    qt.insert(b3); 
    qt.insert(b4);
    SpatialPartitionBubbles sp = new SpatialPartitionBubbles(bubbles, qt);
    sp.handleCollisionsUsingQt(b1);
    //b1 touches b3 and b4 but not b2 - so b1,b3,b4 get popped
    assertTrue(bubbles.get(1) == null && bubbles.get(2) != null && bubbles.get(3) == null && bubbles.get(4) == null);
  }
}
