
package imt2018517;
import java.util.ArrayList;
import animation.*;
public class DemoSceneObject extends SceneObject 
{
	  private Point p;
	  private Point destination;
	  public static int Case=0;
	  public	ArrayList<Point> k= new ArrayList<Point>();
	  public static int c=0;
	  private String myname="ps";
	  public DemoSceneObject(){
		p= new Point(0,0);
		destination= new Point(0,0);
    myname =myname+c;
    c++;
	}
	@Override
  public String getObjName() 
  {
		  return (myname);
	}
  @Override
  public Point getPosition() 
  {
		    return p;
	}
  @Override
  public void setPosition(int x, int y) 
  {
		  p.setPos(x, y);
	}
  public void setDestPosition(int x, int y)
  {
		    destination.setPos(x, y);
	}
	@Override
  public BBox getBBox() 
  {
		return new DemoBBox(p,new Point(p.getX()+7,p.getY()+7));
	}
  @Override
	protected ArrayList<Point> getOutline() {
		k.add(p);
		k.add(new Point(p.getX()+10,p.getY()));
    k.add(new Point(p.getX()+10,p.getY()+15));
    k.add(new Point(p.getX(),p.getY()+15));
		return k;
	}
  
	@Override
  public synchronized void updatePos(int deltaT) 
 {
  if(Math.abs(destination.getX()-p.getX())<Math.abs(30) && Math.abs(destination.getY()-p.getY())<Math.abs(20))
   Scene.getScene().getActors().remove(this);
  if (p.getX()<0 || p.getY()<0)
   Scene.getScene().getActors().remove(this);  
  Scene s;
  s=Scene.getScene();
  float dx,dy;
  float slope=(destination.getY()-(float)(p.getY()))/(destination.getX()-(float)(p.getX()));
  if(slope>1)
  {
    dy=20;
    if(destination.getY()<p.getY())
      dy*=-1;
    dx=dy/slope;
  }
  else
  {
    dx=20;
    if(destination.getX()<p.getX())
      dx*=-1;
    dy=dx*slope;
  }
  boolean colide=false;
  ArrayList<SceneObject> allobj = new ArrayList<SceneObject>();
  allobj.addAll(s.getObstacles());
  allobj.addAll(s.getActors());
  for(SceneObject sc : allobj)
  {
   if(sc.getBBox().intersects(new DemoBBox( new Point(getPosition().getX()+(int)dx,getPosition().getY()+(int)dy) ,new Point(getPosition().getX()+(int)dx+15,getPosition().getY()+(int)dy+10)) ) )
    colide=true;
  }
  while(colide)
  {
    for(SceneObject sc : allobj)
      {
        if(sc.getBBox().intersects( new DemoBBox(new Point(getPosition().getX()+(int)dx,getPosition().getY()+(int)dy) , new Point(getPosition().getX()+(int)dx+15,getPosition().getY()+(int)dy+10)) ) )
          {
            colide=true;
            break;
          }
        else
          {
            colide=false;
          }
      }
        if(Case==0)
            dx=-dx;     
          else if(Case==1)
          {
            dx=-dx;
            dy=-dy;
          }
       else if(Case==2)
          {
            dy=-dy;
            dx=+dx;
            dy=+dy;
          }
      else if(Case==3)
        {
            dx=0;
            dy=0;
            break;
        }
  Case+=1;
 }
  setPosition(getPosition().getX()+(int)dx,getPosition().getY()+(int)dy);
    Case=0;
    slope=(destination.getY()-(float)(p.getY()))/(destination.getX()-(float)(p.getX()));
    if(slope>1)
    {
     dy=20;
     if(destination.getY()<p.getY())
      dy*=-1;
     dx=dy/slope;
    }
    else
    {
     dx=20;
     if(destination.getX()<p.getX())
      dx*=-1;
     dy=dx*slope;
    }
}}



