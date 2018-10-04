import java.util.Hashtable;

public abstract class SpatialPartitionGeneric<T> {

    Hashtable<Integer, T> playerPositions;
    QuadTree qTree;

    abstract void handleCollisionsFor(T obj);
}