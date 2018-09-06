package com.iluwatar.gameloop;

public class IndependentAnimation extends BallAnimation {

    public IndependentAnimation() {
        super(new InterpolatedGamePanel());
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
            updateView(interpolation);
        }

    }

    @Override
    public void updateState() {
        gPanel.update();
    }

    public void updateView(float interpolation) {
        gPanel.setInterpolation(interpolation);
        gPanel.repaint();
    }

    public static void main(String[] args) {
        IndependentAnimation ballAnimation = new IndependentAnimation();
        ballAnimation.setVisible(true);
        ballAnimation.run();
    }
}
