package imt2018517;

import animation.*;

public class DemoBBox implements BBox
{
    private Point bottomLeft;
    private Point topRight;
    public int top;
    public int left;
    public int bottom;
    public int right;
    public DemoBBox(Point p1,Point p2)
    {
        
        this.bottomLeft =new Point(0,0);
        this.topRight=new Point(0,0);    }
    public Point getMinPt()
    {
        
        return bottomLeft;
    }
    public Point getMaxPt()
    {
       return topRight;
    }
    public boolean intersects(BBox b)
    {
    
            if (this.topRight.getY() < b.getMinPt().getY() 
              || this.bottomLeft.getY() > b.getMaxPt().getY()) 
              {
                return false;
              }
            if (this.topRight.getX() < b.getMinPt().getX() 
              || this.bottomLeft.getX() > b.getMaxPt().getX()) 
              {
                return false;
              }
            return true;
        
    }
}
