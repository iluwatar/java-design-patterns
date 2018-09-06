package com.iluwatar.gameloop;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

class InterpolatedGamePanelTest {

    @Test
    void isBasicPanel() {
        InterpolatedGamePanel panel = new InterpolatedGamePanel();
        assertThat(panel, instanceOf(BasicGamePanel.class));
    }

    @Test
    void setInterpolation() {
        float interpolation = 10000;
        InterpolatedGamePanel panel = new InterpolatedGamePanel();
        panel.setInterpolation(interpolation);
        assert panel.getInterpolation() == interpolation;
    }
}