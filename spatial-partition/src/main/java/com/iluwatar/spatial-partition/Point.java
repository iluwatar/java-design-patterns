import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Point<T> {

    public int x; public int y; //coordinates
    public int id; //to keep track in hashtable

    abstract void move();
    abstract boolean touches(T obj);
    abstract void handleCollision(ArrayList<Point> pointsToCheck, Hashtable<Integer, T> allPoints);
}
