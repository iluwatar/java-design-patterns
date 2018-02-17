package com.iluwatar.sam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Class represent rocket view class. This class is part of java swing gui library.
 * The class contains buttons that control the operation of the counter, as well as the 
 * display of the value of the counter and the state rocket counter.
 *
 */
public class RocketView extends JFrame {
  
  private static RocketView instance = null;
  private RocketActions actions;
  private JButton btnStart;
  private JButton btnAbort;
  private JButton btnPrepare;
  private JLabel lbl;
  
  private RocketView() {
  }
  
  /**
   * Method for GUI initialization and actions for buttons.
   */
  private void initialise() {
    setSize(400, 300);
    setLocationRelativeTo(null);
    setTitle("Rocket Counter");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    lbl = new JLabel("Rocket is in IDLE state. Press 'Prepare' for prepare counter.");
    btnStart = new JButton("Start Counter");
    btnStart.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.startAction();
      }
    });
    
    btnAbort = new JButton("Abort Counter");
    btnAbort.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.abortAction();
      }
    });
    
    btnPrepare = new JButton("Prepare Counter");
    btnPrepare.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        actions.prepareAction();
      }
    });
    
    JPanel panel1 = new JPanel();
    add(panel1);
    btnStart.setVisible(false);
    btnAbort.setVisible(false);
    panel1.add(lbl);
    panel1.add(btnStart);
    panel1.add(btnAbort);
    panel1.add(btnPrepare);
  }
  
  /**
   * 
   * @return Instance of rocket view. 
   */
  public static RocketView getInstance() {
    if (instance == null) {
      instance = new RocketView();
      instance.initialise();
    }
    return instance;
  }
  
  /**
   * This method serves to hide or show the buttons depending on the condition in which the rocket counter is.
   * @param stateRepresentation from rocket state
   */
  public void display(String stateRepresentation) {
    lbl.setText(stateRepresentation);
    if (this.actions.getRocketModel().getStateEngine().getCurrentState() instanceof CountingState) {
      btnStart.setVisible(false);
      btnAbort.setVisible(true);
    } else if (this.actions.getRocketModel().getStateEngine().getCurrentState() instanceof LaunchedState) {
      btnAbort.setVisible(false);
    } else if (this.actions.getRocketModel().getStateEngine().getCurrentState() instanceof IdleState) {
      btnAbort.setVisible(false);
      btnStart.setVisible(false);
      btnPrepare.setVisible(true);
    } else if (this.actions.getRocketModel().getStateEngine().getCurrentState() instanceof ReadyState) {
      btnPrepare.setVisible(false);
      btnStart.setVisible(true);
    } else if (this.actions.getRocketModel().getStateEngine().getCurrentState() instanceof AbortedState) {
      btnAbort.setVisible(false);
    }
  }
  
  public void setRocketActions(RocketActions actions) {
    this.actions = actions;
  }
  
  public RocketActions getRocketActions() {
    return this.actions;
  }
}
