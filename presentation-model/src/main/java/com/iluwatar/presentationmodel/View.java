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
package com.iluwatar.presentationmodel;

import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Generates the GUI of albums.
 */
@Getter
@Slf4j
public class View {
  /**
   * the model that controls this view.
   */
  private final PresentationModel model;

  /**
   * the filed to show and modify title.
    */
  private TextField txtTitle;
  /**
   * the filed to show and modify the name of artist.
   */
  private TextField txtArtist;
  /**
   * the checkbox for is classical.
   */
  private JCheckBox chkClassical;
  /**
   * the filed to show and modify composer.
   */
  private TextField txtComposer;
  /**
   * a list to show all the name of album.
   */
  private JList<String> albumList;
  /**
   * a button to apply of all the change.
   */
  private JButton apply;
  /**
   * roll back the change.
   */
  private JButton cancel;

  /**
   * the value of the text field size.
   */
  static final int WIDTH_TXT = 200;
  static final int HEIGHT_TXT = 50;

  /**
   * the value of the GUI size and location.
   */
  static final int LOCATION_X = 200;
  static final int LOCATION_Y = 200;
  static final int WIDTH = 500;
  static final int HEIGHT = 300;

  /**
   * constructor method.
   */
  public View() {
    model = new PresentationModel(PresentationModel.albumDataSet());
  }

  /**
   * save the data to PresentationModel.
   */
  public void saveToMod() {
    LOGGER.info("Save data to PresentationModel");
    model.setArtist(txtArtist.getText());
    model.setTitle(txtTitle.getText());
    model.setIsClassical(chkClassical.isSelected());
    model.setComposer(txtComposer.getText());
  }

  /**
   * load the data from PresentationModel.
   */
  public void loadFromMod() {
    LOGGER.info("Load data from PresentationModel");
    txtArtist.setText(model.getArtist());
    txtTitle.setText(model.getTitle());
    chkClassical.setSelected(model.getIsClassical());
    txtComposer.setEditable(model.getIsClassical());
    txtComposer.setText(model.getComposer());
  }

  /**
   * initialize the GUI.
   */
  public void createView() {
    var frame = new JFrame("Album");
    var b1 = Box.createHorizontalBox();

    frame.add(b1);
    albumList = new JList<>(model.getAlbumList());
    albumList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        model.setSelectedAlbumNumber(albumList.getSelectedIndex() + 1);
        loadFromMod();
      }
    });
    b1.add(albumList);

    var b2 = Box.createVerticalBox();
    b1.add(b2);

    txtArtist = new TextField();
    txtTitle = new TextField();

    txtArtist.setSize(WIDTH_TXT, HEIGHT_TXT);
    txtTitle.setSize(WIDTH_TXT, HEIGHT_TXT);

    chkClassical = new JCheckBox();
    txtComposer = new TextField();
    chkClassical.addActionListener(itemEvent -> {
      txtComposer.setEditable(chkClassical.isSelected());
      if (!chkClassical.isSelected()) {
        txtComposer.setText("");
      }
    });
    txtComposer.setSize(WIDTH_TXT, HEIGHT_TXT);
    txtComposer.setEditable(model.getIsClassical());

    apply = new JButton("Apply");
    apply.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        saveToMod();
        loadFromMod();
      }
    });
    cancel = new JButton("Cancel");
    cancel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        loadFromMod();
      }
    });

    b2.add(txtArtist);
    b2.add(txtTitle);

    b2.add(chkClassical);
    b2.add(txtComposer);

    b2.add(apply);
    b2.add(cancel);

    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    frame.setBounds(LOCATION_X, LOCATION_Y, WIDTH, HEIGHT);
    frame.setVisible(true);
  }

}
