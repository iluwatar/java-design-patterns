package com.iluwatar.bloc;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * The BlocUI class handles the creation and management of the UI components.
 */
public class BlocUi {

  /**
   * Creates and shows the UI.
   */
  public void createAndShowUi() {
    // Create a Bloc instance to manage the state
    final Bloc bloc = new Bloc();

    // setting up a frame window with a title
    JFrame frame = new JFrame("BloC example");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(400, 300);

    //  label to display the counter value
    JLabel counterLabel = new JLabel("Counter: 0", SwingConstants.CENTER);
    counterLabel.setFont(new Font("Arial", Font.BOLD, 20));

    // buttons for increment, decrement, and toggling listener
    JButton decrementButton = new JButton("Decrement");
    JButton toggleListenerButton = new JButton("Disable Listener");
    JButton incrementButton = new JButton("Increment");

    frame.setLayout(new BorderLayout());
    frame.add(counterLabel, BorderLayout.CENTER);
    frame.add(incrementButton, BorderLayout.NORTH);
    frame.add(decrementButton, BorderLayout.SOUTH);
    frame.add(toggleListenerButton, BorderLayout.EAST);

    // making a state listener to update the counter label when the state changes
    StateListener<State> stateListener = state -> counterLabel.setText("Counter: " + state.value());

    // adding the listener to the Bloc instance
    bloc.addListener(stateListener);

    toggleListenerButton.addActionListener(e -> {
      if (bloc.getListeners().contains(stateListener)) {
        bloc.removeListener(stateListener);
        toggleListenerButton.setText("Enable Listener");
      } else {
        bloc.addListener(stateListener);
        toggleListenerButton.setText("Disable Listener");
      }
    });

    incrementButton.addActionListener(e -> bloc.increment());
    decrementButton.addActionListener(e -> bloc.decrement());

    frame.setVisible(true);
  }
}