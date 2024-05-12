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
package com.iluwatar.intercepting.filter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 * This is where the requests are displayed after being validated by filters.
 *
 * @author mjoshzambales
 */
public class Target extends JFrame { //NOSONAR

  @Serial
  private static final long serialVersionUID = 1L;

  private final JTable jt;
  private final DefaultTableModel dtm;
  private final JButton del;

  /**
   * Constructor.
   */
  public Target() {
    super("Order System");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(640, 480);
    dtm = new DefaultTableModel(
        new Object[]{"Name", "Contact Number", "Address", "Deposit Number", "Order"}, 0);
    jt = new JTable(dtm);
    del = new JButton("Delete");
    setup();
  }

  private void setup() {
    setLayout(new BorderLayout());
    var bot = new JPanel();
    add(jt.getTableHeader(), BorderLayout.NORTH);
    bot.setLayout(new BorderLayout());
    bot.add(del, BorderLayout.EAST);
    add(bot, BorderLayout.SOUTH);
    var jsp = new JScrollPane(jt);
    jsp.setPreferredSize(new Dimension(500, 250));
    add(jsp, BorderLayout.CENTER);

    del.addActionListener(new TargetListener());

    var rootPane = SwingUtilities.getRootPane(del);
    rootPane.setDefaultButton(del);
    setVisible(true);
  }

  public void execute(String[] request) {
    dtm.addRow(new Object[]{request[0], request[1], request[2], request[3], request[4]});
  }

  class TargetListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      var temp = jt.getSelectedRow();
      if (temp == -1) {
        return;
      }
      var temp2 = jt.getSelectedRowCount();
      IntStream.range(0, temp2).forEach(i -> dtm.removeRow(temp));
    }
  }
}
