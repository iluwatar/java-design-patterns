package com.iluwater.component;


public class BjornInputComponent implements InputComponent {

    @Override
    public void update(GameObject gameObject) {
        gameObject.setPositionOFx(gameObject.getPositionOFx() + gameObject.getVelocity());
        gameObject.setPositionOFy(gameObject.getPositionOFy() + gameObject.getVelocity());
    }
}
