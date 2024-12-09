package com.iluwatar.bloc;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;




/**
 * The Main class demonstrates the use of the Bloc pattern in a simple GUI application.
 * It creates a JFrame with buttons to increment, decrement, and toggle a listener
 * that updates the counter value on the screen.
 */
public class Main {

  /**
   * The entry point of the application. Initializes the GUI and sets up actions
   * for the buttons and listener management.
   *
   * @param args command-line arguments (not used in this example)
   */
  public static void main(String[] args) {
    // Create a Bloc instance to manage the state


    // Create and set up the JFrame
    JFrame frame = new JFrame("BloC example");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(400, 300);

    // Create a label to display the counter value
    JLabel counterLabel = new JLabel("Counter: 0", SwingConstants.CENTER);
    counterLabel.setFont(new Font("Arial", Font.BOLD, 20));

    // Create buttons for increment, decrement, and toggling listener
    JButton incrementButton = new JButton("Increment");
    JButton decrementButton = new JButton("Decrement");
    JButton toggleListenerButton = new JButton("Disable Listener");

    // Set layout and add components to the frame
    frame.setLayout(new BorderLayout());
    frame.add(counterLabel, BorderLayout.CENTER);
    frame.add(incrementButton, BorderLayout.NORTH);
    frame.add(decrementButton, BorderLayout.SOUTH);
    frame.add(toggleListenerButton, BorderLayout.EAST);
    Bloc bloC = new Bloc();
    // Create a state listener to update the counter label when the state changes
    StateListener<State> stateListener = state -> counterLabel.setText("Counter: " + state.getValue());

    // Add the listener to the Bloc instance
    bloC.addListener(stateListener);

    // Set action listeners for buttons
    toggleListenerButton.addActionListener(e -> {
      if (bloC.getListeners().contains(stateListener)) {
        bloC.removeListener(stateListener);
        toggleListenerButton.setText("Enable Listener");
      } else {
        bloC.addListener(stateListener);
        toggleListenerButton.setText("Disable Listener");
      }
    });

    incrementButton.addActionListener(e -> bloC.increment());
    decrementButton.addActionListener(e -> bloC.decrement());

    // Make the frame visible
    frame.setVisible(true);
  }
}
