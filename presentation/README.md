---
layout: pattern
title: Presentation
folder: presentation
permalink: /patterns/presentation/
categories: Behavioral
tags:
 - Decoupling
---
## Also known as
Application Model

## Intent
Presentation Model pulls the state and behavior of the view out into a model class that is part of the presentation. The Presentation Model coordinates with the domain layer and provides an interface to the view that minimizes decision making in the view. The view either stores all its state in the Presentation Model or synchronizes its state with Presentation Model frequently

## Explanation

Example

> When we need to write a program with GUI,  there is no need for us to put all presentation behavior in the view class. Because it will test become harder. So we can use Presentation Model Pattern to separate the behavior and view. The view only need to load the data and states from other class and show these data on the screen according to the states.

Code Example

Class `view` is the GUI of albums. Methods `saveToPMod` and `loadFromPMod` are used to achieve synchronization.

```java
/**
 * Generates the GUI of albums.
 */
@Getter
@Slf4j
public class View {
  /**
   * the model that controls this view.
   */
  private final PresentationMod model;

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
   * constructor method.
   */
  public View() {
    model = new PresentationMod(PresentationMod.albumDataSet());
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

    final var widthTxt = 200;
    final var heightTxt = 50;
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

    final var x = 200;
    final var y = 200;
    final var width = 500;
    final var height = 300;
    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    frame.setBounds(x, y, width, height);
    frame.setVisible(true);
  }

}

```

Class `Album` is to store information of a album.

```java
@Setter
@Getter
@AllArgsConstructor
public class Album {
  /**
   * the title of the album.
   */
  private String title;
  /**
   * the artist name of the album.
   */
  private String artist;
  /**
   * is the album classical, true or false.
   */
  private boolean isClassical;
  /**
   * only when the album is classical,
   * composer can have content.
   */
  private String composer;
}

```

Class `DsAlbum` is store the information of all the albums that will be shown on GUI.

```java
/**
 * a class used to deal with albums.
 */
@Slf4j
@Getter
public class DsAlbum {
  /**
   * albums a list of albums.
   */
  private final List<Album> albums;

  /**
   * a constructor method.
   */
  public DsAlbum() {
    this.albums = new ArrayList<>();
  }

  /**
   * a method used to add a new album to album list.
   *
   * @param title       the title of the album.
   * @param artist      the artist name of the album.
   * @param isClassical is the album classical, true or false.
   * @param composer    only when the album is classical,
   *                    composer can have content.
   */
  public void addAlbums(final String title,
                        final String artist, final boolean isClassical,
                        final String composer) {
    if (isClassical) {
      this.albums.add(new Album(title, artist, true, composer));
    } else {
      this.albums.add(new Album(title, artist, false, ""));
    }
  }
}

```

 Class `PresentationMod` is used to control all the action of GUI.

```java
/**
 * The class between view and albums, it is used to control the data.
 */
@Slf4j
public class PresentationMod {
  /**
   * the data of all albums that will be shown.
   */
  private final DsAlbum data;
  /**
   * the no of selected album.
   */
  private int selectedAlbumNumber;
  /**
   * the selected album.
   */
  private Album selectedAlbum;

  /**
   * Generates a set of data for testing.
   *
   * @return a instance of DsAlbum which store the data.
   */
  public static DsAlbum albumDataSet() {
    var titleList = new String[]{"HQ", "The Rough Dancer and Cyclical Night",
                                 "The Black Light", "Symphony No.5"};
    var artistList = new String[]{"Roy Harper", "Astor Piazzola",
                                  "The Black Light", "CBSO"};
    var isClassicalList = new boolean[]{false, false, false, true};
    var composerList = new String[]{null, null, null, "Sibelius"};

    var result = new DsAlbum();
    for (var i = 1; i <= titleList.length; i++) {
      result.addAlbums(titleList[i - 1], artistList[i - 1],
              isClassicalList[i - 1], composerList[i - 1]);
    }
    return result;
  }

  /**
   * constructor method.
   *
   * @param dataOfAlbums the data of all the albums
   */
  public PresentationMod(final DsAlbum dataOfAlbums) {
    this.data = dataOfAlbums;
    this.selectedAlbumNumber = 1;
    this.selectedAlbum = this.data.getAlbums().get(0);
  }

  /**
   * Changes the value of selectedAlbumNumber.
   *
   * @param albumNumber the number of album which is shown on the view.
   */
  public void setSelectedAlbumNumber(final int albumNumber) {
    LOGGER.info("Change select number from {} to {}",
            this.selectedAlbumNumber, albumNumber);
    this.selectedAlbumNumber = albumNumber;
    this.selectedAlbum = data.getAlbums().get(this.selectedAlbumNumber - 1);
  }

  /**
   * get the title of selected album.
   *
   * @return the tile of selected album.
   */
  public String getTitle() {
    return selectedAlbum.getTitle();
  }

  /**
   * set the title of selected album.
   *
   * @param value the title which user want to user.
   */
  public void setTitle(final String value) {
    LOGGER.info("Change album title from {} to {}",
            selectedAlbum.getTitle(), value);
    selectedAlbum.setTitle(value);
  }

  /**
   * get the artist of selected album.
   *
   * @return the artist of selected album.
   */
  public String getArtist() {
    return selectedAlbum.getArtist();
  }

  /**
   * set the name of artist.
   *
   * @param value the name want artist to be.
   */
  public void setArtist(final String value) {
    LOGGER.info("Change album artist from {} to {}",
            selectedAlbum.getArtist(), value);
    selectedAlbum.setArtist(value);
  }

  /**
   * Gets a boolean value which represents whether the album is classical.
   *
   * @return is the album classical.
   */
  public boolean getIsClassical() {
    return selectedAlbum.isClassical();
  }

  /**
   * set the isClassical of album.
   *
   * @param value is the album classical.
   */
  public void setIsClassical(final boolean value) {
    LOGGER.info("Change album isClassical from {} to {}",
            selectedAlbum.isClassical(), value);
    selectedAlbum.setClassical(value);
  }

  /**
   * get is classical of the selected album.
   *
   * @return is the album classical.
   */
  public String getComposer() {
    return selectedAlbum.isClassical() ? selectedAlbum.getComposer() : "";
  }

  /**
   * Sets the name of composer when the album is classical.
   *
   * @param value the name of composer.
   */
  public void setComposer(final String value) {
    if (selectedAlbum.isClassical()) {
      LOGGER.info("Change album composer from {} to {}",
              selectedAlbum.getComposer(), value);
      selectedAlbum.setComposer(value);
    } else {
      LOGGER.info("Composer can not be changed");
    }
  }

  /**
   * Gets a list of albums.
   *
   * @return the names of all the albums.
   */
  public String[] getAlbumList() {
    var result = new String[data.getAlbums().size()];
    for (var i = 0; i < result.length; i++) {
      result[i] = data.getAlbums().get(i).getTitle();
    }
    return result;
  }
}

```

We can run class `App` to start this demo. the checkbox is the album classical; the first text field is the name of album artist; the second is the name of album title; the last one is the name of the composer:

![](./etc/result.png)


## Class diagram
![](./etc/presentation.urm.png "presentation model")

## Applicability
Use the Presentation Model Pattern when

* Testing a presentation through a GUI window is often awkward, and in some cases impossible.
* Do not determine which GUI will be used.

## Related patterns

- [Supervising Controller](https://martinfowler.com/eaaDev/SupervisingPresenter.html) 
- [Passive View](https://martinfowler.com/eaaDev/PassiveScreen.html)

## Credits

* [Presentation Model Patterns](https://martinfowler.com/eaaDev/PresentationModel.html)

