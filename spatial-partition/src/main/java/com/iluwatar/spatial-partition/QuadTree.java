import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class QuadTree {
    Rect boundary;
    int capacity; //after which quadtree gets divided
    boolean divided; //whether already divided
    Hashtable<Integer, Point> points; //points in quadtree
    //4 divisions
    QuadTree northwest;
    QuadTree northeast;
    QuadTree southwest;
    QuadTree southeast;

    public QuadTree(Rect boundary, int capacity){
        this.boundary = boundary;
        this.capacity = capacity;
        this.divided = false;
        this.points = new Hashtable<Integer, Point>();
        this.northwest = null; this.northeast = null; this.southwest = null; this.southeast = null;
    }

    public void insert(Point p) {
        if (!this.boundary.contains(p)) {
            return;
        }
        else {
            if (this.points.size()<this.capacity) {
                points.put(p.id, p);
            }
            else {
                if (!this.divided) {
                    this.divide();
                }

                //point goes to one of the divisions
                if (this.northwest.boundary.contains(p)) {
                    this.northwest.insert(p);
                }
                else if (this.northeast.boundary.contains(p)) {
                    this.northeast.insert(p);
                }
                else if (this.southwest.boundary.contains(p)) {
                    this.southwest.insert(p);
                }
                else if (this.southeast.boundary.contains(p)) {
                    this.southeast.insert(p);
                }
            }
        }
    }

    void divide() {
        Rect nw = new Rect(this.boundary.x-this.boundary.width/4, this.boundary.y+this.boundary.height/4, this.boundary.width/2, this.boundary.height/2);
        this.northwest = new QuadTree(nw , this.capacity);
        Rect ne = new Rect(this.boundary.x+this.boundary.width/4, this.boundary.y+this.boundary.height/4, this.boundary.width/2, this.boundary.height/2);
        this.northeast = new QuadTree(ne , this.capacity);
        Rect sw = new Rect(this.boundary.x-this.boundary.width/4, this.boundary.y-this.boundary.height/4, this.boundary.width/2, this.boundary.height/2);
        this.southwest = new QuadTree(sw , this.capacity);
        Rect se = new Rect(this.boundary.x+this.boundary.width/4, this.boundary.y-this.boundary.height/4, this.boundary.width/2, this.boundary.height/2);
        this.southeast = new QuadTree(se , this.capacity);
        this.divided = true;
    }

    public ArrayList<Point> query(Rect r, ArrayList<Point> relevantPoints) {
        //finding all points that lie within given rectangle
        //could also be a circle instead of a rectangle
        if (this.boundary.intersects(r)) { //if not returns empty arraylist
            for (Enumeration<Integer> e = this.points.keys(); e.hasMoreElements();) {
                Integer i = e.nextElement();
                if (r.contains(this.points.get(i))) {
                    relevantPoints.add(this.points.get(i));
                }
            }
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
