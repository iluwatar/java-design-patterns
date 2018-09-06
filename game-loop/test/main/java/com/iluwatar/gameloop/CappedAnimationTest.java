package com.iluwatar.gameloop;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class CappedAnimationTest {

    @Test
    void isInstanceOfBallAnimation() {
        CappedAnimation animation = new CappedAnimation();
        assertThat(animation, instanceOf(BallAnimation.class));
    }
}