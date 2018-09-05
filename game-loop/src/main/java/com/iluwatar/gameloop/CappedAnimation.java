package com.iluwatar.gameloop;

import java.awt.*;

public class CappedAnimation extends BallAnimation {

    public CappedAnimation() {
        super(Loop.CAPPED_FPS);
    }

    public void gameLoop() {

        final long ticksPerSecond = 50;
        final long skipTicks = 1000 / ticksPerSecond;
        final int maxFrameSkip = 10;

        long nextUpdateTick = System.currentTimeMillis();
        int loops;

        while (true) {
            loops = 0;
            while (System.currentTimeMillis() > nextUpdateTick && loops < maxFrameSkip) {
                updateState();
                nextUpdateTick += skipTicks;
                loops++;
            }
            updateView();
            Toolkit.getDefaultToolkit().sync();
        }
    }

    public static void main(String[] args) {
        BallAnimation ballAnimation = new CappedAnimation();
        ballAnimation.setVisible(true);
        ballAnimation.run();
    }

}
