package com.iluwater.component;

/**
 * BjornInputComponent is a class for our main game star
 * This class creat a Input component for Bjorn.
 */

public class BjornInputComponent implements InputComponent {

    /**
     * This method is a logger for Bjorn when happens a Input update.
     * In real scenario, there will be code for dealing with IO.
     *
     * @param gameObject is a object in the game, here it is Bjorn
     */

    @Override
    public void update(GameObject gameObject) {
        gameObject.setPositionOFx(gameObject.getPositionOFx() + gameObject.getVelocity());
        gameObject.setPositionOFy(gameObject.getPositionOFy() + gameObject.getVelocity());
    }
}
