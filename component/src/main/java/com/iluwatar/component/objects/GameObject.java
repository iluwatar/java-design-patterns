package com.iluwatar.component.objects;

import com.iluwatar.component.GraphicsComponent;
import com.iluwatar.component.InputComponent;
import com.iluwatar.component.PhysicsComponent;
import com.iluwatar.component.enums.DirectionEnum;

public class GameObject {

	private int velocity;
	private int xCoordinate;
	private int yCoordinate;
	private DirectionEnum joyStickDirection;

	InputComponent inputComp;
	PhysicsComponent physicsComp;
	GraphicsComponent graphicsComp;

	public GameObject(InputComponent inputComp,PhysicsComponent physicsComp,GraphicsComponent graphicsComp) {
		this.inputComp = inputComp;
		this.physicsComp = physicsComp;
		this.graphicsComp = graphicsComp;
	}

	public void update(DirectionEnum joyStickDirection) {
		this.setJoyStickDirection(joyStickDirection);
		inputComp.update(this);
		physicsComp.update(this);
		graphicsComp.update(this);
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public int getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public DirectionEnum getJoyStickDirection() {
		return joyStickDirection;
	}

	public void setJoyStickDirection(DirectionEnum joyStickDirection) {
		this.joyStickDirection = joyStickDirection;
	}

}
