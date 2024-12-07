package com.iluwatar.bloc;
import javax.swing.*;
import java.awt.*;

public class Main
{
  public static void main(String[] args)
  {
    Bloc bloC = new Bloc();
    JFrame frame = new JFrame("BloC example");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    JLabel counterLabel = new JLabel("Counter: 0", SwingConstants.CENTER);
    counterLabel.setFont(new Font("Arial", Font.BOLD, 20));
    JButton incrementButton = new JButton("Increment");
    JButton decrementButton = new JButton("Decrement");
    JButton toggleListenerButton = new JButton("Disable Listener");
    frame.setLayout(new BorderLayout());
    frame.add(counterLabel, BorderLayout.CENTER);
    frame.add(incrementButton, BorderLayout.NORTH);
    frame.add(decrementButton, BorderLayout.SOUTH);
    frame.add(toggleListenerButton, BorderLayout.EAST);

    StateListener<State> stateListener = state -> counterLabel.setText("Counter: " + state.getValue());

    bloC.addListener(stateListener);

    toggleListenerButton.addActionListener(e ->
    {
      if (bloC.getListeners().contains(stateListener))
      {
        bloC.removeListener(stateListener);
        toggleListenerButton.setText("Enable Listener");
      } else
      {
        bloC.addListener(stateListener);
        toggleListenerButton.setText("Disable Listener");
      }
    }
    );
    incrementButton.addActionListener(e -> bloC.increment());
    decrementButton.addActionListener(e -> bloC.decrement());
    frame.setVisible(true);
  }
}
