package com.iluwatar.gameloop;


import org.junit.jupiter.api.Test;

import javax.swing.JPanel;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class BasicGamePanelTest {

    @Test
    void isPanel() {
        BasicGamePanel panel = new BasicGamePanel();
        assertThat(panel, instanceOf(JPanel.class));
    }
}
