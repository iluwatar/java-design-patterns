package com.iluwatar.gameloop;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class BallAnimationTest {

    @Test
    void hasBasicGamePanel() {
        BallAnimation animation = new BallAnimation();
        assertThat(animation.gPanel, instanceOf(BasicGamePanel.class));
    }
}