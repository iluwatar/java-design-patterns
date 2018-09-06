package com.iluwatar.gameloop;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicAnimationTest {

    @Test
    void isInstanceOfBallAnimation() {
        BasicAnimation animation = new BasicAnimation();
        assertThat(animation, instanceOf(BallAnimation.class));
    }

}