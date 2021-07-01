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

import java.security.SecureRandom;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>The idea behind the <b>Spatial Partition</b> design pattern is to enable efficient location
 * of objects by storing them in a data structure that is organised by their positions. This is
 * especially useful in the gaming world, where one may need to look up all the objects within a
 * certain boundary, or near a certain other object, repeatedly. The data structure can be used to
 * store moving and static objects, though in order to keep track of the moving objects, their
 * positions will have to be reset each time they move. This would mean having to create a new
 * instance of the data structure each frame, which would use up additional memory, and so this
 * pattern should only be used if one does not mind trading memory for speed and the number of
 * objects to keep track of is large to justify the use of the extra space.</p>
 * <p>In our example, we use <b>{@link QuadTree} data structure</b> which divides into 4 (quad)
 * sub-sections when the number of objects added to it exceeds a certain number (int field
 * capacity). There is also a
 * <b>{@link Rect}</b> class to define the boundary of the quadtree. We use an abstract class
 * <b>{@link Point}</b>
 * with x and y coordinate fields and also an id field so that it can easily be put and looked up in
 * the hashmap. This class has abstract methods to define how the object moves (move()), when to
 * check for collision with any object (touches(obj)) and how to handle collision
 * (handleCollision(obj)), and will be extended by any object whose position has to be kept track of
 * in the quadtree. The <b>{@link SpatialPartitionGeneric}</b> abstract class has 2 fields - a
 * hashmap containing all objects (we use hashmap for faster lookups, insertion and deletion)
 * and a quadtree, and contains an abstract method which defines how to handle interactions between
 * objects using the quadtree.</p>
 * <p>Using the quadtree data structure will reduce the time complexity of finding the objects
 * within a certain range from <b>O(n^2) to O(nlogn)</b>, increasing the speed of computations
 * immensely in case of large number of objects, which will have a positive effect on the rendering
 * speed of the game.</p>
 */

@Slf4j
public class App {
  private static final String BUBBLE = "Bubble ";

  static void noSpatialPartition(int numOfMovements, HashMap<Integer, Bubble> bubbles) {
    //all bubbles have to be checked for collision for all bubbles
    var bubblesToCheck = bubbles.values();

    //will run numOfMovement times or till all bubbles have popped
    while (numOfMovements > 0 && !bubbles.isEmpty()) {
      bubbles.forEach((i, bubble) -> {
        // bubble moves, new position gets updated
        // and collisions are checked with all bubbles in bubblesToCheck
        bubble.move();
        bubbles.replace(i, bubble);
        bubble.handleCollision(bubblesToCheck, bubbles);
      });
      numOfMovements--;
    }
    //bubbles not popped
    bubbles.keySet().stream().map(key -> BUBBLE + key + " not popped").forEach(LOGGER::info);
  }

  static void withSpatialPartition(
      int height, int width, int numOfMovements, HashMap<Integer, Bubble> bubbles) {
    //creating quadtree
    var rect = new Rect(width / 2D, height / 2D, width, height);
    var quadTree = new QuadTree(rect, 4);

    //will run numOfMovement times or till all bubbles have popped
    while (numOfMovements > 0 && !bubbles.isEmpty()) {
      //quadtree updated each time
      bubbles.values().forEach(quadTree::insert);
      bubbles.forEach((i, bubble) -> {
        //bubble moves, new position gets updated, quadtree used to reduce computations
        bubble.move();
        bubbles.replace(i, bubble);
        var sp = new SpatialPartitionBubbles(bubbles, quadTree);
        sp.handleCollisionsUsingQt(bubble);
      });
      numOfMovements--;
    }
    //bubbles not popped
    bubbles.keySet().stream().map(key -> BUBBLE + key + " not popped").forEach(LOGGER::info);
  }

  /**
   * Program entry point.
   *
   * @param args command line args
   */

  public static void main(String[] args) {
    var bubbles1 = new HashMap<Integer, Bubble>();
    var bubbles2 = new HashMap<Integer, Bubble>();
    var rand = new SecureRandom();
    for (int i = 0; i < 10000; i++) {
      var b = new Bubble(rand.nextInt(300), rand.nextInt(300), i, rand.nextInt(2) + 1);
      bubbles1.put(i, b);
      bubbles2.put(i, b);
      LOGGER.info(BUBBLE, i, " with radius ", b.radius,
          " added at (", b.coordinateX, ",", b.coordinateY + ")");
    }

    var start1 = System.currentTimeMillis();
    App.noSpatialPartition(20, bubbles1);
    var end1 = System.currentTimeMillis();
    var start2 = System.currentTimeMillis();
    App.withSpatialPartition(300, 300, 20, bubbles2);
    var end2 = System.currentTimeMillis();
    LOGGER.info("Without spatial partition takes ", (end1 - start1), "ms");
    LOGGER.info("With spatial partition takes ", (end2 - start2), "ms");
  }
}

