package com.iluwatar.gameloop;

import javax.swing.*;
import java.awt.*;

public class BasicGamePanel extends JPanel {

    int ballX, ballY, lastBallX, lastBallY;
    int ballWidth, ballHeight;
    private float ballXVel, ballYVel;
    private double bounceDepreciation = 1;

    int lastDrawX, lastDrawY;

    public void setInterpolation(float interpolation) {}

    BasicGamePanel() {

        ballX = lastBallX = 100;
        ballY = lastBallY = 100;
        ballWidth = 25;
        ballHeight = 25;
        float ballSpeed = 100;
        double randomX = Math.random();
        randomX = randomX > 0.5 ? randomX - 0.25 : randomX + 0.25;
        double randomY = Math.random();
        randomY = randomY > 0.5 ? randomY - 0.25 : randomY + 0.25;
        ballXVel = (float) randomX * ballSpeed *2 - ballSpeed;
        ballYVel = (float) randomY * ballSpeed *2 - ballSpeed;
    }

    void update() {

        lastBallX = ballX;
        lastBallY = ballY;

        ballX += ballXVel;
        ballY += ballYVel;

        if (ballX + ballWidth/2 >= getWidth()) {
            bounceDepreciation *= 0.999;
            ballXVel *= -bounceDepreciation;
            ballX = getWidth() - ballWidth/2;

        } else if (ballX - ballWidth/2 <= 0) {
            bounceDepreciation *= 0.999;
            ballXVel *= -bounceDepreciation;
            ballX = ballWidth/2;
        }

        if (ballY + ballHeight/2 >= getHeight()) {
            bounceDepreciation *= 0.999;
            ballYVel *= -bounceDepreciation;
            ballY = getHeight() - ballHeight/2;

        } else if (ballY - ballHeight/2 <= 0) {
            bounceDepreciation *= 0.999;
            ballYVel *= -bounceDepreciation;
            ballY = ballHeight/2;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, ballWidth, ballHeight);

        lastDrawX = ballX;
        lastDrawY = ballY;
    }
}