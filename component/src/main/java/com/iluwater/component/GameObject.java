package com.iluwater.component;

import java.util.ArrayList;

public class GameObject {
    public int velocity;
    public int positionOFx;
    public int positionOFy;
    ArrayList<Component> componentArrayList;
    public GameObject(ArrayList<Component> componentArrayList){
        this.componentArrayList = componentArrayList;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setPositionOFx(int positionOFx) {
        this.positionOFx = positionOFx;
    }

    public int getPositionOFx() {
        return positionOFx;
    }

    public void setPositionOFy(int positionOFy) {
        this.positionOFy = positionOFy;
    }

    public int getPositionOFy() {
        return positionOFy;
    }

    public void update(){
        for (Component component : componentArrayList) {
            component.update(this);
        }
    }
}
