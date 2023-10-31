
import animation.Scene;
import animation.SceneObject;
import animation.View;

public class DemoTextView extends View {

	@Override
	public void clear() {
		System.out.println(" Clearing View \n");
	}

	@Override
	public void render(SceneObject s) {
		System.out.println("Object " + s.getObjName() + " at " + s.getPosition());
	}

	@Override
	public void init() {
		Scene.getScene().animate();
	}

	@Override
	public void updateView() {
		
	}

}
