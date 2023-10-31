package imt2018027;
import java.util.*;
import animation.*;

public class DemoScene extends Scene
{
  public DemoScene()
  {
      super();
  }
  @Override
  protected void checkScene()
  {
    ArrayList<SceneObject> actors = getActors();
    ArrayList<SceneObject> obstacles = getObstacles();
    for(int i=0; i<actors.size(); i++)
    {
      for(int j=0; j<actors.size(); j++)
      {
        if(actors.get(i) != actors.get(j))
        {
          if(actors.get(i).getBBox().intersects(actors.get(j).getBBox()))
          {
            actors.remove(i);
            break;
          }
        }
      }
      for(int j=0; j<obstacles.size(); j++)
      {
        if(actors.get(i).getBBox().intersects(obstacles.get(j).getBBox()))
        {
          actors.remove(i);
          break;
        }
      }
    }
  }
}
