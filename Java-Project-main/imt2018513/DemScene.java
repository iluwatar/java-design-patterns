package imt2018513;


import animation.Scene;
import animation.*;

// import demo.DemSceneObject;

public class DemScene extends Scene{
     public DemScene(){
          super();
     }
     protected void checkScene(){
          // ArrayList<SceneObject> all = new ArrayList<>();
          // all.addAll(getActors());
          // all.addAll(getObstacles());
          for(SceneObject ac:getActors()){
               for(SceneObject sc:getObstacles()){
                    if(ac.getBBox().intersects(sc.getBBox())){
                       //  ac.updatePos(10);
                    
                    }
               }
          }
          

     }

}
