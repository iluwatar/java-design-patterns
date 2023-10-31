import animation.Scene;
import animation.SceneObject;
import animation.View;
import imt2018517.*;
import imt2018010.*;
import imt2018027.*;
import imt2018513.*;
import imt2018081.*;
public class Demo {

   public static void main(String[] args) {
	   Scene scene = new imt2018517.DemoScene(); 
	   for (int i = 0; i < 1; i++) {
		   SceneObject s = new imt2018517.DemoSceneObject(); 
		   s.setPosition(i * 50, i * 50);
		   scene.addObstacle(s); 
	   }
	   for (int i = 1; i < 2; i++) {
		SceneObject s = new imt2018010.DemoSceneObject(); 
		s.setPosition(i * 50, i * 50);
		scene.addObstacle(s); 
	}
	for (int i = 2; i < 3; i++) {
		SceneObject s = new imt2018027.DemoSceneObject(); 
		s.setPosition(i * 50, i * 50);
		scene.addObstacle(s); 
	}
	for (int i = 3; i < 4; i++) {
		SceneObject s = new DemSceneObject(); 
		s.setPosition(i * 50, i * 50);
		scene.addObstacle(s); 
	}
	for (int i = 4; i < 5; i++) {
		SceneObject s = new TestObject(); 
		s.setPosition(i * 50, i * 50);
		scene.addObstacle(s); 
	}

	   for (int i = 0; i < 1; i++) {
		   SceneObject s = new imt2018517.DemoSceneObject();
													  
		   s.setPosition(500 - i * 50, 300 + i * 50); 
		   s.setDestPosition(0, 0);
		   scene.addActor(s); 
	   }
	   for (int i = 1; i < 2; i++) {
		   SceneObject s = new imt2018027.DemoSceneObject();
													  
		   s.setPosition(500 - i * 50, 300 + i * 50); 
		   s.setDestPosition(0, 0);
		   scene.addActor(s); 
	   }
	   for (int i = 2; i < 3; i++) {
		SceneObject s = new imt2018010.DemoSceneObject();
												   
		s.setPosition(500 - i * 50, 300 + i * 50); 
		s.setDestPosition(0, 0);
		scene.addActor(s); 
	}
	for (int i = 3; i < 4; i++) {
		SceneObject s = new DemSceneObject();
												   
		s.setPosition(500 - i * 50, 300 + i * 50); 
		s.setDestPosition(0, 0);
		scene.addActor(s); 
	}
	for (int i = 4; i < 5; i++) {
		SceneObject s = new TestObject();
												   
		s.setPosition(500 - i * 50, 300 + i * 50); 
		s.setDestPosition(0, 0);
		scene.addActor(s); 
	}
	   View view = new DemoSwingView();
	   scene.setView(view);
	   view.init();

   }

}
