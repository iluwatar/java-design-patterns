package animation;

public class Point {
	
	public int getX() {
		return px;
	}

	public int getY() {
		return py;
	}

	public void setPos(int x, int y) {
		px = x;
		py = y;
	}


	public Point(int x, int y) {
		px = x;
		py = y;
	}
	
	public Point(Point p) {
		px = p.px;
		py = p.py;
	}
	
	private int px, py;
}
