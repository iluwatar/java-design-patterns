/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

package com.iluwatar.spatial-partition;

/**
 * The idea behind the Spatial Partition Design Pattern is to enable efficient location
 * of objects by storing them in a data structure that is organised by their positions.
 * This is especially useful in the gaming world, where one may need to look up all the
 * objects within a certain boundary, or near a certain other object. This can be used to
 * store moving and static objects, though in order to keep track of the moving objects,
 * their positions will have to be reset each time they move. This would mean having to create
 * a new instance of the data structure each time an object moves, which would use up additional
 * memory, and so this pattern should only be used if the number of objects to keep track of is large
 * and one does not mind trading memory for speed.
 * In our example, we use QuadTree data structure which divides into sub-sections when the
 * number of objects added to it exceeds a certain number (int field capacity). There is also a Rect
 * class to define the boundary of the quadtree. We use an abstract class Point with x and y coordinate
 * fields and also an id field so that it can easily be put and looked up in the hashtable. This class
 * will be extended by any object whose position has to be kept track of, and has abstract methods to
 * define how the object moves (move()), when to check for collision with any object (touches(obj)) and
 * how to handle collision (handleCollision(obj)). The SpatialPartitionGeneric abstract class has to
 * be initiated using the hashtable containing all objects (we use hashtable for faster lookups,
 * insertion and deletion) and a quadtree and contains an abstract method which defines how to
 * handle any interaction between objects.
 * Using the quadtree data structure will reduce the time complexity of finding the objects within a
 * certain range from O(n^2) to O(nlogn), increasing the speed of computations immensely in case of
 * large number of objects, and will have a positive effect on the video rendering speed.
 */

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

public class App {

    static Hashtable<Integer, Bubble> noSpatialPartition(int height, int width, int numOfMovements, Hashtable<Integer, Bubble> bubbles){
        ArrayList<Point> bubblesToCheck = new ArrayList<Point>();
        for (Enumeration<Integer> e = bubbles.keys(); e.hasMoreElements();) {
            bubblesToCheck.add(bubbles.get(e.nextElement())); //all bubbles have to be checked for collision for all bubbles
        }

        //will run numOfMovement times or till all bubbles have popped
        while (numOfMovements>0 && !bubbles.isEmpty()) {
            for (Enumeration<Integer> e = bubbles.keys(); e.hasMoreElements();) {
                Integer i = e.nextElement();
                //bubble moves, new position gets updated, collisions checked with all bubbles in bubblesToCheck
                bubbles.get(i).move();  bubbles.replace(i, bubbles.get(i));
                bubbles.get(i).handleCollision(bubblesToCheck, bubbles);
            }
            numOfMovements--;
        }
        for (Integer key : bubbles.keySet()) {
            //bubbles not popped
            System.out.println("Bubble " + key + " not popped");
        }
        return bubbles;
    }

    static Hashtable<Integer, Bubble> withSpatialPartition(int height, int width, int numOfMovements, Hashtable<Integer, Bubble> bubbles){
        //creating quadtree
        Rect rect = new Rect(width/2,height/2,width,height);
        QuadTree qTree = new QuadTree(rect, 4);

        //will run numOfMovement times or till all bubbles have popped
        while (numOfMovements>0 && !bubbles.isEmpty()) {
            //quadtree updated each time
            for (Enumeration<Integer> e = bubbles.keys(); e.hasMoreElements();) {
                qTree.insert(bubbles.get(e.nextElement()));
            }
            for (Enumeration<Integer> e = bubbles.keys(); e.hasMoreElements();) {
                Integer i = e.nextElement();
                //bubble moves, new position gets updated, quadtree used to reduce computations
                bubbles.get(i).move();  bubbles.replace(i, bubbles.get(i));
                SpatialPartition_Bubbles sp = new SpatialPartition_Bubbles(bubbles, qTree);
                sp.handleCollisionsUsingQT(bubbles.get(i));
            }
            numOfMovements--;
        }
        for (Integer key : bubbles.keySet()) {
            //bubbles not popped
            System.out.println("Bubble " + key + " not popped");
        }
        return bubbles;
    }

    public static void main(String[] args) throws InterruptedException {
        Hashtable<Integer, Bubble> bubbles1 = new Hashtable<Integer, Bubble>();
        Hashtable<Integer, Bubble> bubbles2 = new Hashtable<Integer, Bubble>();
        Random rand = new Random();
        for (int i=0; i<10000; i++) {
            Bubble b = new Bubble(rand.nextInt(300), rand.nextInt(300), i, rand.nextInt(2)+1);
            bubbles1.put(i, b);
            bubbles2.put(i, b);
            System.out.println("Bubble " + i + " with radius " + b.radius + " added at (" + b.x + "," + b.y + ")");
        }

        long start1 = System.currentTimeMillis();
        App.noSpatialPartition(300,300,20,bubbles1);
        long end1 = System.currentTimeMillis();
        System.out.println("a");
        System.out.println("a");
        long start2 = System.currentTimeMillis();
        App.withSpatialPartition(300,300,20,bubbles2);
        long end2 = System.currentTimeMillis();
        System.out.println("Without spatial partition takes " + (end1 - start1) + "ms");
        System.out.println("With spatial partition takes " + (end2 - start2) + "ms");
    }
}
