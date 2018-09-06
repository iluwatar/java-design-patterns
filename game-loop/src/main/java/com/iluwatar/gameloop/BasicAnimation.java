package com.iluwatar.gameloop;

public class BasicAnimation extends BallAnimation {

    public BasicAnimation() {
        super();
    }

    public void gameLoop() {
        while (true) {
            updateState();
            updateView();
        }
    }

    public static void main(String[] args) {
        BallAnimation ballAnimation = new BasicAnimation();
        ballAnimation.setVisible(true);
        ballAnimation.run();
    }
}
