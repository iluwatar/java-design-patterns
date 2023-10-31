package imt2018081;
import animation.*;
public class TestBBox implements BBox
{
    private Point min_p;
    private Point max_p;
 
    public TestBBox(Point p1,Point p2){
        min_p=p1;
        max_p=p2;
    }
    
    public Point getMinPt(){
        return min_p;
    }
    
    public Point getMaxPt(){
        return max_p;
    }
    
    
    public boolean intersects(BBox Box){
        Point p_min,p_max;
        p_max=Box.getMaxPt();
        p_min=Box.getMinPt();
        if(p_min.getY()>max_p.getY())
        return false;
        else if(min_p.getY()>p_max.getY())
        return false;
        else if(p_max.getX()<min_p.getX())
        return false;
        else if(max_p.getX()<p_min.getX())
        return false;
        else 
        return true;
    }
}
