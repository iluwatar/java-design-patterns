package com.iluwatar.gameloop;

import java.awt.*;

import static java.lang.Thread.sleep;

public class LockedAnimation extends BallAnimation {

    public LockedAnimation() {
        super(Loop.LOCKED);
    }

    public void gameLoop() {

        final int framesPerSecond = 25;
        final long skipTicks = 1000 / framesPerSecond;

        long sleepPeriod;
        long nextUpdateTick = System.currentTimeMillis();

        while (true) {

            updateState();
            updateView();
            Toolkit.getDefaultToolkit().sync();

            nextUpdateTick += skipTicks;
            sleepPeriod = nextUpdateTick - System.currentTimeMillis();

            if (sleepPeriod >= 0) {
                try {
                    sleep(sleepPeriod);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BallAnimation ballAnimation = new BallAnimation(Loop.LOCKED);
        ballAnimation.setVisible(true);
        ballAnimation.run();
    }
}
