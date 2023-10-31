package imt2018081;
import animation.*;
import java.util.ArrayList;
public class TestObject extends SceneObject
{
  private String Name;
  private Point position;
  private Point Dest;
  private static int value=0;
  private static int dir=0;
  
  
  public TestObject()
  {
    value++;
    Name="O"+(value);
    position=new Point(0,0);
    Dest=new Point(0,0);
  }
  
  public String getObjName()
  {
    return Name;
  }

  public Point getPosition()
  {
    return position;
  }
  
  public void setPosition(int x, int y)
  {
    position.setPos(x,y);
  }

  public void setDestPosition(int x, int y)
  {
    Dest.setPos(x,y);
  }
  
  public BBox getBBox()
  {
    return new TestBBox(new Point(position),new Point(position.getX()+20,position.getY()+20));
  }
  
  public ArrayList<Point> getOutline()
  {
    ArrayList<Point> array=new ArrayList<>(4);
    array.add(new Point(position));
    array.add(new Point(position.getX()+20,position.getY()));
    array.add(new Point(position.getX()+20,position.getY()+20));
    array.add(new Point(position.getX(),position.getY()+20));
    return array;
  }
  
  public void updatePos(int deltaT) 
  {
    if(Math.abs(Dest.getX()-position.getX())<Math.abs(20)){
      if(Math.abs(Dest.getY()-position.getY())<Math.abs(20)){
        Scene.getScene().getActors().remove(this);
      }
    }
    
    if (position.getX()<=0 || position.getY()<=0){
      Scene.getScene().getActors().remove(this);
    }
    
    Scene scene;
    scene=Scene.getScene();
    float dx,dy;
    float slope=(Dest.getY()-(float)(position.getY()))/(Dest.getX()-(float)(position.getX()));
    
    if(slope>1)
    {
      dy=25;
      if(Dest.getY()<position.getY()){
        dy*=-1;
      }
      dx=dy/slope;
    }
    else{
      dx=25;
      if(Dest.getX()<position.getX()){
        dx*=-1;
      }
      dy=dx*slope;
    }

    boolean collision=false;
    TestBBox TB;
    Point  point1;
    Point  point2;
    ArrayList<SceneObject> list = new ArrayList<SceneObject>();
    list.addAll(scene.getObstacles());
    list.addAll(scene.getActors());

    for(SceneObject scene_obj : list)
    {
      if(scene_obj.getBBox().intersects( TB=new TestBBox( point1= new Point(getPosition().getX()+(int)dx,getPosition().getY()+(int)dy) , point2= new Point(getPosition().getX()+(int)dx+20,getPosition().getY()+(int)dy+20)) ) ){
        collision=true;
      }  
    }
    
    while(collision)
    {
      for(SceneObject scene_obj : scene.getObstacles())
      {
        if(scene_obj.getBBox().intersects( TB=new TestBBox( point1= new Point(getPosition().getX()+(int)dx,getPosition().getY()+(int)dy) , point2= new Point(getPosition().getX()+(int)dx+20,getPosition().getY()+(int)dy+20)) ) )
        {
          collision=true;
          break;
        }
        else
        {
          collision=false;
        }
      }
      if(dir==0)
      {
        dx=-dx;
      }     
      else if(dir==1)
      {
        dx=-dx;
        dy=-dy;
      }
      else if(dir==2)
      {
        dx=0;
        dy=0;
        break;
      }
      dir+=1;
    }

    setPosition(getPosition().getX()+(int)dx,getPosition().getY()+(int)dy);
    dir=0;
    slope=(Dest.getY()-(float)(position.getY()))/(Dest.getX()-(float)(position.getX()));
    
    if(slope>1)
    {
      dy=25;
      if(Dest.getY()<position.getY())
      {
        dy*=-1;
      }
      dx=dy/slope;
    }
    else
    {
      dx=25;
      if(Dest.getX()<position.getX())
      {
        dx*=-1;
      }
      dy=dx*slope;
    }    
  }
}
