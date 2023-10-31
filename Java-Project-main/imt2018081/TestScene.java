package imt2018081;
import animation.*;
import java.util.*;

public class TestScene extends Scene
{
    public TestScene()
    {
        super();
    }
    
    public void checkScene()
    {
        ArrayList<SceneObject> obs_array = getObstacles();
        ArrayList<SceneObject> act_array = getActors();
        for(int i=0; i<act_array.size(); i++)
        {
            for(int j=0; j<act_array.size(); j++)
            {
                if(act_array.get(i) != act_array.get(j))
                {
                    if(act_array.get(i).getBBox().intersects(act_array.get(j).getBBox()))
                    {
                        act_array.remove(i);
                        break;
                    }
                }
            }
            for(int j=0; j<obs_array.size(); j++)
            {
                if(act_array.get(i).getBBox().intersects(obs_array.get(j).getBBox()))
                {
                    act_array.remove(i);
                    break;
                }
            }
        }
    
    }
}
