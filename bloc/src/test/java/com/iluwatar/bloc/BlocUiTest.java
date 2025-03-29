/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.bloc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import javax.swing.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlocUiTest {

  private JFrame frame;
  private JLabel counterLabel;
  private JButton incrementButton;
  private JButton decrementButton;
  private JButton toggleListenerButton;
  private Bloc bloc;
  private StateListener<State> stateListener;

  @BeforeEach
  public void setUp() {
    bloc = new Bloc(); // Re-initialize the Bloc for each test

    frame = new JFrame("BloC example");
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setSize(400, 300);

    counterLabel = new JLabel("Counter: 0", SwingConstants.CENTER);
    counterLabel.setFont(new Font("Arial", Font.BOLD, 20));

    incrementButton = new JButton("Increment");
    decrementButton = new JButton("Decrement");
    toggleListenerButton = new JButton("Disable Listener");

    frame.setLayout(new BorderLayout());
    frame.add(counterLabel, BorderLayout.CENTER);
    frame.add(incrementButton, BorderLayout.NORTH);
    frame.add(decrementButton, BorderLayout.SOUTH);
    frame.add(toggleListenerButton, BorderLayout.EAST);

    stateListener = state -> counterLabel.setText("Counter: " + state.value());
    bloc.addListener(stateListener);

    incrementButton.addActionListener(e -> bloc.increment());
    decrementButton.addActionListener(e -> bloc.decrement());
    toggleListenerButton.addActionListener(
        e -> {
          if (bloc.getListeners().contains(stateListener)) {
            bloc.removeListener(stateListener);
            toggleListenerButton.setText("Enable Listener");
          } else {
            bloc.addListener(stateListener);
            toggleListenerButton.setText("Disable Listener");
          }
        });

    frame.setVisible(true);
  }

  @AfterEach
  public void tearDown() {
    frame.dispose();
    bloc = new Bloc(); // Reset Bloc state after each test to avoid state carryover
  }

  @Test
  public void testIncrementButton() {
    simulateButtonClick(incrementButton);
    assertEquals("Counter: 1", counterLabel.getText());
  }

  @Test
  public void testDecrementButton() {
    simulateButtonClick(decrementButton);
    assertEquals("Counter: -1", counterLabel.getText());
  }

  @Test
  public void testToggleListenerButton() {
    // Disable listener
    simulateButtonClick(toggleListenerButton);
    simulateButtonClick(incrementButton);
    assertEquals("Counter: 0", counterLabel.getText()); // Listener is disabled

    // Enable listener
    simulateButtonClick(toggleListenerButton);
    simulateButtonClick(incrementButton);
    assertEquals("Counter: 2", counterLabel.getText()); // Listener is re-enabled
  }

  private void simulateButtonClick(JButton button) {
    for (var listener : button.getActionListeners()) {
      listener.actionPerformed(null);
    }
  }
}
