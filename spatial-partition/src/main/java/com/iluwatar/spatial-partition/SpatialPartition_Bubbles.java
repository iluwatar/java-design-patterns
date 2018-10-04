import java.util.ArrayList;
import java.util.Hashtable;

public class SpatialPartition_Bubbles extends SpatialPartitionGeneric<Bubble>{

    Hashtable<Integer, Bubble> bubbles;
    QuadTree qTree;

    SpatialPartition_Bubbles(Hashtable<Integer, Bubble> bubbles, QuadTree qTree){
        this.bubbles = bubbles;
        this.qTree = qTree;
    }

    void handleCollisionsFor(Bubble b) {
        //finding points within area of a square drawn with centre same as centre of bubble and length = radius of bubble
        Rect rect = new Rect(b.x, b.y, 2*b.radius, 2*b.radius);
        ArrayList<Point> quadTreeQueryResult = new ArrayList<Point>();
        this.qTree.query(rect, quadTreeQueryResult);
        //handling these collisions
        b.handleCollision(quadTreeQueryResult, this.bubbles);
    }

}
