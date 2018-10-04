import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

public class App {

    static void noSpatialPartition(int numOfBubbles, int height, int width, int numOfMovements, int maxRadius){
        Hashtable<Integer, Bubble> bubbles = new Hashtable<Integer, Bubble>();
        ArrayList<Point> bubblesToCheck = new ArrayList<Point>();
        Random rand = new Random();

        //creating bubbles
        for (int i=0; i<numOfBubbles; i++) {
            Bubble b = new Bubble(rand.nextInt(width), rand.nextInt(height), i, rand.nextInt(maxRadius)+1);
            bubbles.put(i, b);
            bubblesToCheck.add(b); //all bubbles have to be checked for collision for all bubbles
            System.out.println("Bubble " + i + " with radius " + b.radius + " added at (" + b.x + "," + b.y + ")");
        }

        //will run numOfMovement times or till all bubbles have popped
        while (numOfMovements>0 && !bubbles.isEmpty()) {
            for (Enumeration<Integer> e = bubbles.keys(); e.hasMoreElements();) {
                Integer i = e.nextElement();
                //bubble moves, new position gets updated, collisions checked with all bubbles in bubblesToCheck
                bubbles.get(i).move();
                bubbles.replace(i, bubbles.get(i));
                bubbles.get(i).handleCollision(bubblesToCheck, bubbles);
            }
            numOfMovements--;
        }
        for (Integer key : bubbles.keySet()) {
            //bubbles not popped
            System.out.println("Bubble " + key + " still left");
        }
    }

    static void withSpatialPartition(int numOfBubbles, int height, int width, int numOfMovements, int maxRadius){
        Hashtable<Integer, Bubble> bubbles = new Hashtable<Integer, Bubble>();
        Random rand = new Random();

        //creating bubbles
        for (int i=0; i<numOfBubbles; i++) {
            Bubble b = new Bubble(rand.nextInt(width), rand.nextInt(height), i, rand.nextInt(maxRadius)+1);
            bubbles.put(i, b);
            System.out.println("Bubble " + i + " with radius " + b.radius + " added at (" + b.x + "," + b.y + ")");
        }

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
                bubbles.get(i).move();
                bubbles.replace(i, bubbles.get(i));
                SpatialPartition_Bubbles sp = new SpatialPartition_Bubbles(bubbles, qTree);
                sp.handleCollisionsFor(bubbles.get(i));
            }
            numOfMovements--;
        }
        for (Integer key : bubbles.keySet()) {
            //bubbles not popped
            System.out.println("Bubble " + key + " still left");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start1 = System.currentTimeMillis();
        App.noSpatialPartition(10000,300,300,20,2);
        long end1 = System.currentTimeMillis();
        long start2 = System.currentTimeMillis();
        App.withSpatialPartition(10000,300,300,20,2);
        long end2 = System.currentTimeMillis();
        System.out.println("Without spatial partition takes " + (end1 - start1) + "ms");
        System.out.println("With spatial partition takes " + (end2 - start2) + "ms");
    }
}
