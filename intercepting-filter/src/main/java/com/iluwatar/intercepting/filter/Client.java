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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.Serial;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The Client class is responsible for handling the input and running them through filters inside
 * the {@link FilterManager}.
 *
 * <p>This is where {@link Filter}s come to play as the client pre-processes the request before
 * being displayed in the {@link Target}.
 *
 * @author joshzambales
 */
public class Client extends JFrame { // NOSONAR

  @Serial
  private static final long serialVersionUID = 1L;

  private transient FilterManager filterManager;
  private final JLabel jl;
  private final JTextField[] jtFields;
  private final JTextArea[] jtAreas;
  private final JButton clearButton;
  private final JButton processButton;

  /**
   * Constructor.
   */
  public Client() {
    super("Client System");
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setSize(300, 300);
    jl = new JLabel("RUNNING...");
    jtFields = new JTextField[3];
    for (var i = 0; i < 3; i++) {
      jtFields[i] = new JTextField();
    }
    jtAreas = new JTextArea[2];
    for (var i = 0; i < 2; i++) {
      jtAreas[i] = new JTextArea();
    }
    clearButton = new JButton("Clear");
    processButton = new JButton("Process");

    setup();
  }

  private void setup() {
    setLayout(new BorderLayout());
    var panel = new JPanel();
    add(jl, BorderLayout.SOUTH);
    add(panel, BorderLayout.CENTER);
    panel.setLayout(new GridLayout(6, 2));
    panel.add(new JLabel("Name"));
    panel.add(jtFields[0]);
    panel.add(new JLabel("Contact Number"));
    panel.add(jtFields[1]);
    panel.add(new JLabel("Address"));
    panel.add(jtAreas[0]);
    panel.add(new JLabel("Deposit Number"));
    panel.add(jtFields[2]);
    panel.add(new JLabel("Order"));
    panel.add(jtAreas[1]);
    panel.add(clearButton);
    panel.add(processButton);

    clearButton.addActionListener(e -> {
      Arrays.stream(jtAreas).forEach(i -> i.setText(""));
      Arrays.stream(jtFields).forEach(i -> i.setText(""));
    });

    processButton.addActionListener(this::actionPerformed);

    JRootPane rootPane = SwingUtilities.getRootPane(processButton);
    rootPane.setDefaultButton(processButton);
    setVisible(true);
  }

  public void setFilterManager(FilterManager filterManager) {
    this.filterManager = filterManager;
  }

  public String sendRequest(Order order) {
    return filterManager.filterRequest(order);
  }

  private void actionPerformed(ActionEvent e) {
    var fieldText1 = jtFields[0].getText();
    var fieldText2 = jtFields[1].getText();
    var areaText1 = jtAreas[0].getText();
    var fieldText3 = jtFields[2].getText();
    var areaText2 = jtAreas[1].getText();
    var order = new Order(fieldText1, fieldText2, areaText1, fieldText3, areaText2);
    jl.setText(sendRequest(order));
  }
}
