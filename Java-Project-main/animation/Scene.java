package animation;
import java.util.ArrayList;

public abstract class Scene {
	
	public Scene() {
		obstacles = new ArrayList<>();
		actors = new ArrayList<>();
		theScene = this;
	}
	
	public void setView(View v) {
		view = v;
	}
	
	public static Scene getScene() {	
		return theScene;
	}
	
	public void addObstacle(SceneObject so) {
		obstacles.add(so);
	}
	
	public void addActor(SceneObject so) {
		actors.add(so);
	}

	public ArrayList<SceneObject> getObstacles() {
		return obstacles;
	}
	
	public ArrayList<SceneObject> getActors() {
		return actors;
	}
	
	public void render() {
		view.clear();
		for (SceneObject s : obstacles) {
			view.render(s);
		}
		for (SceneObject s : actors) {
			view.render(s);
		}
		
		view.updateView();
	}
	
	public void animate() {
		System.out.println("Starting animation");
		
		for (SceneObject s : actors) {
			s.start();
		}

		for (int i = 0; i < 2 * View.maxUpdates; i++) {
			
			checkScene(); // pre-process the scene before re-rendering
			
			render();
			
			try {
				Thread.sleep(View.delT/2);
			} catch (InterruptedException e) {	
				e.printStackTrace();
			}
		}
	}
	
	// override this if additional processing is needed before each render
	// should flag actors that intersect with other obstacles or objects
	abstract protected void checkScene();
	
	private ArrayList<SceneObject> obstacles;
	private ArrayList<SceneObject> actors;
	
	private View view;
	private static Scene theScene;
}
