package animation;
import java.util.ArrayList;

public abstract class SceneObject extends Thread {

	protected SceneObject() {

	}

	// a unique name for the object
	public abstract String getObjName();

	// return the position of a representative point (local origin) of the object
	// in the window coordinates where it is being displayed
	public abstract Point getPosition();

	public abstract void setPosition(int x, int y);
	
	public abstract void setDestPosition(int x, int y);

	// return the tightest fitting BBox for the shape
	public abstract BBox getBBox();

	// return the path/name of a .png or .jpg file that contains an icon for this
	// object. If non-null, this image will be used by graphical displays to render
	// the object
	public String imageFileName() {
		return null;
	}

	// return the list of points that represent the shape of this object. The points
	// should be ordered so that by joining consecutive points, and joining the last
	// point
	// to the first point in the list, we get a closed outline of the object
	// Note: these points should represent the current position of the object
	protected abstract ArrayList<Point> getOutline();

	// compute the new position of the object after it moves for deltaT
	protected abstract void updatePos(int deltaT);

	@Override
	public void run() {
		// default implementation. You can override this if you want to change the
		// behavior
		// of this object

		for (int i = 0; i < View.maxUpdates; i++) {
			updatePos(View.delT);
			try {
				Thread.sleep(View.delT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
