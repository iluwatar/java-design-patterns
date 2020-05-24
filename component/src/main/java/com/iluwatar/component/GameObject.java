package com.iluwatar.component;

import java.util.ArrayList;

/**
 * GameObject is a class for all object in the game.
 * It was constructed by a collection of component.
 */

public class GameObject {
  private int velocity;
  private int positionOFx;
  private int positionOFy;
  ArrayList<Component> componentArrayList;

  /**
   * Constructor for GameObject
   * @param componentArrayList is the list of this object contains
   */

  public GameObject(ArrayList<Component> componentArrayList){
    this.componentArrayList=new ArrayList<>();
    this.componentArrayList.addAll(componentArrayList);
  }

  /**
   * setter for velocity
   * @param velocity is the velocity of this object
   */

  public void setVelocity(int velocity) {
    this.velocity = velocity;
  }

  /**
   * getter for velocity
   */

  public int getVelocity() {
    return velocity;
  }

  /**
   * setter for PositionOFx
   * @param positionOFx is the PositionOFx of this object
   */

  public void setPositionOFx(int positionOFx) {
    this.positionOFx = positionOFx;
  }


  /**
   * getter for PositionOFx
   */

  public int getPositionOFx() {
    return positionOFx;
  }

  /**
   * setter for PositionOFy
   * @param positionOFy is the PositionOFy of this object
   */

  public void setPositionOFy(int positionOFy) {
    this.positionOFy = positionOFy;
  }

  /**
   * getter for PositionOFy
   */

  public int getPositionOFy() {
    return positionOFy;
  }

  /**
   * update for this object's components.
   */

  public void update(){
    for (Component component : componentArrayList) {
      component.update(this);
    }
  }
}
