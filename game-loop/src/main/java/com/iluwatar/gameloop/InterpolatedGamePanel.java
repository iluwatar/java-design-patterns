package com.iluwatar.gameloop;

import java.awt.*;

public class InterpolatedGamePanel extends BasicGamePanel {

    private float interpolation;

    @Override
    public void setInterpolation(float interpolation) {
        this.interpolation = interpolation;
    }

    float getInterpolation() {
        return interpolation;
    }

    @Override
    protected void paintComponent(Graphics g) {

        // Clearing previous draw by overwriting it
        g.setColor(getBackground());
        g.fillRect(lastDrawX-1, lastDrawY-1, ballWidth+2, ballHeight+2);

        g.setColor(Color.RED);
        int drawX = (int) ((ballX - lastBallX) * interpolation + lastBallX - ballWidth/2);
        int drawY = (int) ((ballY - lastBallY) * interpolation + lastBallY - ballWidth/2);
        g.fillOval(drawX, drawY, ballWidth, ballHeight);

        lastDrawX = drawX;
        lastDrawY = drawY;
    }
}
