package com.iluwatar.presentation;

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
  static final int widthTxt = 200;
  static final int heightTxt = 50;

  /**
   * the value of the GUI size and location.
   */
  static final int locationX = 200;
  static final int locationY = 200;
  static final int width = 500;
  static final int height = 300;

  /**
   * constructor method.
   */
  public View() {
    model = new PresentationModel(PresentationModel.albumDataSet());
  }

  /**
   * save the data to PresentationModel.
   */
  public void saveToPMod() {
    LOGGER.info("Save data to PresentationModel");
    model.setArtist(txtArtist.getText());
    model.setTitle(txtTitle.getText());
    model.setIsClassical(chkClassical.isSelected());
    model.setComposer(txtComposer.getText());
  }

  /**
   * load the data from PresentationModel.
   */
  public void loadFromPMod() {
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
        loadFromPMod();
      }
    });
    b1.add(albumList);

    var b2 = Box.createVerticalBox();
    b1.add(b2);

    txtArtist = new TextField();
    txtTitle = new TextField();

    txtArtist.setSize(widthTxt, heightTxt);
    txtTitle.setSize(widthTxt, heightTxt);

    chkClassical = new JCheckBox();
    txtComposer = new TextField();
    chkClassical.addActionListener(itemEvent -> {
      txtComposer.setEditable(chkClassical.isSelected());
      if (!chkClassical.isSelected()) {
        txtComposer.setText("");
      }
    });
    txtComposer.setSize(widthTxt, heightTxt);
    txtComposer.setEditable(model.getIsClassical());

    apply = new JButton("Apply");
    apply.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        saveToPMod();
        loadFromPMod();
      }
    });
    cancel = new JButton("Cancel");
    cancel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(final MouseEvent e) {
        loadFromPMod();
      }
    });

    b2.add(txtArtist);
    b2.add(txtTitle);

    b2.add(chkClassical);
    b2.add(txtComposer);

    b2.add(apply);
    b2.add(cancel);

    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    frame.setBounds(locationX, locationY, width, height);
    frame.setVisible(true);
  }

}
