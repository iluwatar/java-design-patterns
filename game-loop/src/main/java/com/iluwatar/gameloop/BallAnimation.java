package com.iluwatar.gameloop;

import javax.swing.*;
import java.awt.*;

public class BallAnimation extends JFrame {

    enum Loop {
        BASIC, LOCKED, CAPPED_FPS, INDEPENDENT
    }

    private GPanel gPanel = new GPanel();
    private Loop loopChoice = Loop.LOCKED;

    public BallAnimation() {
        new BallAnimation(Loop.BASIC);
    }

    public BallAnimation(Loop loopType) {

        super("Ball Animation");

        loopChoice = loopType;
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(gPanel, BorderLayout.CENTER);
        setSize(500,500);
    }

    public void run() {

        Thread loopThread = new Thread() {
            public void run() {
                gameLoop();
            }
        };
        loopThread.start();
    }

    public void gameLoop() {}

    public void updateState() {
        gPanel.update();
    }

    public void updateView() {
        gPanel.repaint();
    }

    private class GPanel extends JPanel {

        double randomX;
        double randomY;

        int ballX, ballY, lastBallX, lastBallY;
        int ballWidth, ballHeight;
        float ballXVel, ballYVel;
        float ballSpeed;
        double bounceDepreciation = 1;

        int lastDrawX, lastDrawY;

        public GPanel() {

            ballX = lastBallX = 100;
            ballY = lastBallY = 100;
            ballWidth = 25;
            ballHeight = 25;
            ballSpeed = 100;
            randomX = Math.random();
            randomX = randomX > 0.5 ? randomX - 0.25 : randomX + 0.25;
            randomY = Math.random();
            randomY = randomY > 0.5 ? randomY - 0.25 : randomY + 0.25;
            ballXVel = (float) randomX * ballSpeed*2 - ballSpeed;
            ballYVel = (float) randomY * ballSpeed*2 - ballSpeed;
        }

        public void update() {

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

            g.setColor(Color.BLACK);
        }
    }
}
