package imt2018010;
import java.util.*;
import animation.BBox;
import animation.Point;
import animation.SceneObject;
import animation.Scene;

public class DemoSceneObject extends SceneObject{

	public DemoSceneObject(){
			currentPos = new Point(0, 0);
			destPoint = new Point(0, 0);
			objCounter += 1;
			id = objCounter;
	}

	@Override
	public String getObjName() {
		return "Object " + id;
	}

	@Override
	public Point getPosition() {
		return currentPos;
	}

	@Override
	public void setPosition(int x, int y) {
		currentPos.setPos(x,y);
	}

	public void setDestPosition(int x, int y) {
		destPoint.setPos(x,y);
	}

	@Override
	public BBox getBBox() {
		BBox bbox = new CollisionBox(new Point(currentPos.getX()-5, currentPos.getY()-5), new Point(currentPos.getX()+5, currentPos.getY()+5));
		return bbox;
	}

	@Override
	protected ArrayList<Point> getOutline() {
		//Rectangle outline
		ArrayList<Point> boxOutline = new ArrayList<Point>();
		int cx = currentPos.getX();
		int cy = currentPos.getY();

		boxOutline.add(new Point(cx-5, cy+5));
		boxOutline.add(new Point(cx+5, cy+5));
		boxOutline.add(new Point(cx+5, cy-5));
		boxOutline.add(new Point(cx-5, cy-5));
		return boxOutline;
	}

	//Given a point, it checks if it's safe for actor to go to that point without colliding with any Actors
	private boolean actorTurn(ArrayList<SceneObject> actors, Point direction){
		setPosition(direction.getX(), direction.getY());
		for(SceneObject a: actors){
			if(a == this){
				continue;
			}
			if(a.getBBox().intersects(this.getBBox())){
				return false;
			}
		}
		return true;
	}
	//Given a point, it checks if it's safe for actor to go to that point without colliding with any Obstacles
	private boolean obstacleTurn(ArrayList<SceneObject> obstacles, Point direction){
		setPosition(direction.getX(), direction.getY());
		for(SceneObject a: obstacles){
			if(a.getBBox().intersects(this.getBBox())){
				return false;
			}
		}
		return true;
	}
	
	//randomised collision avoidance algorithm with actors
	private void checkActors(Point initial, Point straight, Point left, Point U, Point right){
		ArrayList<SceneObject> actors = Scene.getScene().getActors();
		if(actorTurn(actors, straight)){
			return;
		}
		Random rand = new Random();
		ArrayList<Point> cases = new ArrayList<Point>();
		cases.add(left);
		cases.add(U);
		cases.add(right);
		while(cases.size() != 0){
			int i = rand.nextInt(cases.size());
			if(actorTurn(actors, cases.get(i))){
				setPosition(cases.get(i).getX(), cases.get(i).getY());
				return;
			}
			else{
				cases.remove(i);
			}
		}
		setPosition(initial.getX(), initial.getY());
	}

	//randomised collision avoidance algorithm with obstacles
	private void checkObstacles(Point initial, Point straight, Point left, Point U, Point right){
		ArrayList<SceneObject> obstacles = Scene.getScene().getObstacles();
		if(obstacleTurn(obstacles, straight)){
			return;
		}
		Random rand = new Random();
		ArrayList<Point> cases = new ArrayList<Point>();
		cases.add(left);
		cases.add(U);
		cases.add(right);
		while(cases.size() != 0){
			int i = rand.nextInt(cases.size());
			if(obstacleTurn(obstacles, cases.get(i))){
				setPosition(cases.get(i).getX(), cases.get(i).getY());
				return;
			}
			else{
				cases.remove(i);
			}
		}
		setPosition(initial.getX(), initial.getY());
	}

	@Override
	protected void updatePos(int deltaT) {
		Scene s = Scene.getScene();

		int cosNum =  destPoint.getX()-currentPos.getX();
		int sinNum =  destPoint.getY()-currentPos.getY();
		double distance = Math.sqrt(cosNum*cosNum + sinNum*sinNum);
		double cosComp = (cosNum/distance);
		double sinComp = (sinNum/distance);
		int xmove = (int)(speed * deltaT/1000.0 * cosComp);
		int ymove = (int)(speed * deltaT/1000.0 * sinComp);
		final int dirX = 1;
		final int dirY = 1;
		if(distance > 5){
			Point newCorrectPos = new Point(currentPos.getX() + dirX * xmove, currentPos.getY() + dirY * ymove);
			Point leftTurn =  new Point(currentPos.getX() + (-dirY) * ymove, currentPos.getY() + (dirX) * xmove);
			Point UTurn =  new Point(currentPos.getX() + (-dirX) * xmove, currentPos.getY() + (-dirY) * ymove);
			Point rightTurn =  new Point(currentPos.getX() + dirY * ymove, currentPos.getY() + (-dirX) * xmove);

			Point inital = new Point(currentPos.getX(), currentPos.getY());

			checkActors(currentPos, newCorrectPos, leftTurn, UTurn, rightTurn);
			checkObstacles(currentPos, newCorrectPos, leftTurn, UTurn, rightTurn);

		}
		else{
			//Scene.getScene().getActors().remove(this);
			//Scene.getScene().getObstacles().add(this);
		}
	}

	private Point currentPos;
	private Point destPoint;
	private static int objCounter = 0;
	private int id;

	private final int speed = 10;
}

