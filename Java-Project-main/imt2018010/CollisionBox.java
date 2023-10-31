package imt2018010;
import animation.*;

class CollisionBox implements BBox{
    //constructor
    CollisionBox(Point min_p, Point max_p){
        minPoint = min_p;
        maxPoint = max_p;
    }
	@Override
  public Point getMinPt(){
        return minPoint;
    }
    	@Override
	public Point getMaxPt(){
        return maxPoint;
    }
    //check if point is inside the bbox
    private static boolean isInBox(BBox b, Point p){
        return (p.getX() >= b.getMinPt().getX() && p.getY() >= b.getMinPt().getY() && p.getX() <= b.getMaxPt().getX() && p.getY() <= b.getMaxPt().getY());
    }
  @Override
	public boolean intersects(BBox b){
        Point boxMinPt = b.getMinPt();
        Point boxMaxPt = b.getMaxPt();

        Point boxTopLeft = new Point(boxMinPt.getX(), boxMaxPt.getY());
        Point boxBottomRight = new Point(boxMaxPt.getX(), boxMinPt.getY());

        Point thisTopLeft = new Point(minPoint.getX(), maxPoint.getY());
        Point thisBottomRight = new Point(maxPoint.getX(), minPoint.getY());

        boolean doesIntersect = (isInBox(this, boxMinPt) || isInBox(this, boxMaxPt) || isInBox(this, boxTopLeft) || isInBox(this, boxBottomRight));
        return doesIntersect || (isInBox(b, minPoint) || isInBox(b, maxPoint) || isInBox(b, thisTopLeft) || isInBox(b, thisBottomRight));
    }

    private Point minPoint;
    private Point maxPoint;
}
