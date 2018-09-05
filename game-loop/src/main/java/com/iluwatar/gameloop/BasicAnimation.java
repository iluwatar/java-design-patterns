package com.iluwatar.gameloop;

import java.awt.*;

public class BasicAnimation extends BallAnimation {

    public BasicAnimation() {
        super(Loop.BASIC);
    }

    public void gameLoop() {
        while (true) {
            updateState();
            updateView();
            Toolkit.getDefaultToolkit().sync();
        }
    }

    public static void main(String[] args) {
        BallAnimation ballAnimation = new BasicAnimation();
        ballAnimation.setVisible(true);
        ballAnimation.run();
    }
}
