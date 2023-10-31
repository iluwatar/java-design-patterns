package imt2018027;
import animation.*;

class CBox implements BBox
{/*this class is responsible for checking if two boxes intersect or not*/
    //constructor
    CBox(Point min_p, Point max_p)
    {
        maxP = max_p;
        minP = min_p;
    }
    @Override
    public Point getMaxPt()
    {
        return maxP;
    }
    @Override
    public Point getMinPt()
    {
        return minP;
    }
    	
    private static boolean isInBox(BBox b, Point p)
    {//checks if a point is inside a box or not
        return (p.getX() >= b.getMinPt().getX() && p.getY() >= b.getMinPt().getY() && p.getX() <= b.getMaxPt().getX() && p.getY() <= b.getMaxPt().getY());
    }
    @Override
    public boolean intersects(BBox b)
    {//checks if any one of the 4 vertices lie inside the other box
        Point bMinP = b.getMinPt();
        Point bMaxP = b.getMaxPt();

        Point bTopL = new Point(bMinP.getX(), bMaxP.getY());
        Point bBottomR = new Point(bMaxP.getX(), bMinP.getY());

        Point thisTopL = new Point(minP.getX(), maxP.getY());
        Point thisBottomR = new Point(maxP.getX(), minP.getY());

        boolean doesIntersect1 = (isInBox(this, bMinP) || isInBox(this, bMaxP) || isInBox(this, bTopL) || isInBox(this, bBottomR));
        boolean doesIntersect2 = (isInBox(b, minP) || isInBox(b, maxP) || isInBox(b, thisTopL) || isInBox(b, thisBottomR));
        return doesIntersect1 || doesIntersect2;
    }

    private Point minP;
    private Point maxP;
}
