package com.iluwatar.gameloop;

import javax.swing.*;
import java.awt.*;

public class BallAnimation extends JFrame {

    BasicGamePanel gPanel;

    public BallAnimation() {
        this(new BasicGamePanel());
    }

    public BallAnimation(BasicGamePanel gamePanel) {

        super("Ball Animation");

        this.gPanel = gamePanel;

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
}
