package com.iluwatar.gameloop;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class IndependentAnimationTest {

    @Test
    void isInstanceOfBallAnimation() {
        IndependentAnimation animation = new IndependentAnimation();
        assertThat(animation, instanceOf(BallAnimation.class));
    }

    @Test
    void hasInterpolatedGamePanel() {
        IndependentAnimation animation = new IndependentAnimation();
        assertThat(animation.gPanel, instanceOf(InterpolatedGamePanel.class));
    }

}