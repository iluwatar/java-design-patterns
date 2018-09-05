package com.iluwatar.gameloop;

import java.awt.*;

public class IndependentAnimation extends BallAnimation {

    public IndependentAnimation() {
        super(Loop.INDEPENDENT);
    }

    public void gameLoop() {

        final long ticksPerSecond = 25;
        final long skipTicks = 1000 / ticksPerSecond;
        final int maxFrameSkip = 5;

        long nextUpdateTick = System.currentTimeMillis();
        int loops;
        float interpolation;

        while (true) {
            loops = 0;
            while (System.currentTimeMillis() > nextUpdateTick && loops < maxFrameSkip) {
                updateState();
                nextUpdateTick += skipTicks;
                loops++;
            }
            interpolation =
                    (System.currentTimeMillis() + skipTicks - nextUpdateTick)
                            / (float) skipTicks;
//            updateView(interpolation); // TODO: SEPERATE EXAMPLE W/ INTERPOLATION
            updateView();
            Toolkit.getDefaultToolkit().sync();
        }

    }

    public static void main(String[] args) {
        BallAnimation ballAnimation = new IndependentAnimation(); // TODO: Finish independent
        ballAnimation.setVisible(true);
        ballAnimation.run();
    }
}
