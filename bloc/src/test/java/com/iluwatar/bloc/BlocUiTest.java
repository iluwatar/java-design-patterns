package com.iluwatar.bloc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.assertEquals;

public class BlocUiTest {

  private JFrame frame;
  private JLabel counterLabel;
  private JButton incrementButton;
  private JButton decrementButton;
  private JButton toggleListenerButton;
  private Bloc bloc;
  private StateListener<State> stateListener;

  @Before
  public void setUp() {
    bloc = new Bloc();  // Re-initialize the Bloc for each test

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
    toggleListenerButton.addActionListener(e -> {
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

  @After
  public void tearDown() {
    frame.dispose();
    bloc = new Bloc();  // Reset Bloc state after each test to avoid state carryover
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
