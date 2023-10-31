package imt2018513;

import animation.*;

public class DemBBox implements BBox{
    private Point bottomLeft;
    private Point topRight;
    public int top;
    public int left;
    public int bottom;
    public int right;
    //private int x,y,width=1,height=2;
    public DemBBox(Point p1,Point p2){
        
        this.bottomLeft =new Point(0,0);
        this.topRight=new Point(0,0);
        try{
        bottomLeft.setPos(p1.getX(),p1.getY());

        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
         topRight.setPos(p2.getX(),p2.getY());
        }
         catch(Exception e){
             e.printStackTrace();
         }
    }
    public Point getMinPt(){
        
        return bottomLeft;
    }
	
	// return the top-right corner of the bounding box
	public Point getMaxPt(){
       
        return topRight;
    }
	
	// does this box intersect/overlap the input BBox
	public boolean intersects(BBox b){
    
            if (this.topRight.getY() < b.getMinPt().getY() 
              || this.bottomLeft.getY() > b.getMaxPt().getY()) {
                return false;
            }
            if (this.topRight.getX() < b.getMinPt().getX() 
              || this.bottomLeft.getX() > b.getMaxPt().getX()) {
                return false;
            }
            return true;
        
    }
}
