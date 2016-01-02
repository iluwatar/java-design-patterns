package com.iluwatar.intercepting.filter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * This is where the requests are displayed after being validated by filters.
 * 
 * @author mjoshzambales
 *
 */
public class Target extends JFrame {

  private static final long serialVersionUID = 1L;

  private JTable jt;
  private JScrollPane jsp;
  private DefaultTableModel dtm;
  private JButton del;

  /**
   * Constructor
   */
  public Target() {
    super("Order System");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(640, 480);
    dtm =
        new DefaultTableModel(new Object[] {"Name", "Contact Number", "Address", "Deposit Number",
            "Order"}, 0);
    jt = new JTable(dtm);
    del = new JButton("Delete");
    setup();
  }

  private void setup() {
    setLayout(new BorderLayout());
    JPanel bot = new JPanel();
    add(jt.getTableHeader(), BorderLayout.NORTH);
    bot.setLayout(new BorderLayout());
    bot.add(del, BorderLayout.EAST);
    add(bot, BorderLayout.SOUTH);
    jsp = new JScrollPane(jt);
    jsp.setPreferredSize(new Dimension(500, 250));
    add(jsp, BorderLayout.CENTER);

    del.addActionListener(new DListener());

    JRootPane rootPane = SwingUtilities.getRootPane(del);
    rootPane.setDefaultButton(del);
    setVisible(true);
  }

  public void execute(String[] request) {
    dtm.addRow(new Object[] {request[0], request[1], request[2], request[3], request[4]});
  }

  class DListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int temp = jt.getSelectedRow();
      if (temp == -1) {
        return;
      }
      int temp2 = jt.getSelectedRowCount();
      for (int i = 0; i < temp2; i++) {
        dtm.removeRow(temp);
      }
    }
  }
}
