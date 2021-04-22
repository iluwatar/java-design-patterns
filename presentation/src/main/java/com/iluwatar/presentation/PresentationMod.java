package com.iluwatar.presentation;

/**
 * The class between view and albums, it is used to control the data.
 */
public class PresentationMod {
  private final DsAlbum data;
  private int selectedAlbumNumber;
  private Album selectedAlbum;

  /**
   * Generates a set of data for testing.
   *
   * @return a instance of DsAlbum which store the data.
   */
  public static DsAlbum albumDataSet() {
    var result = new DsAlbum();
    result.addAlbums(1, "HQ", "Roy Harper", false, null);
    result.addAlbums(2, "The Rough Dancer and Cyclical Night", "Astor Piazzola", false, null);
    result.addAlbums(3, "The Black Light", "Calexico", false, null);
    result.addAlbums(4, "Symphony No.5", "CBSO", true, "Sibelius");
    result.acceptChanges();
    return result;
  }

  /**
   * constructor method.
   */
  public PresentationMod(DsAlbum data) {
    this.data = data;
    this.selectedAlbumNumber = 1;
    this.selectedAlbum = data.getAlbums().get(0);
  }

  /**
   * Changes the value of selectedAlbumNumber.
   *
   * @param selectedAlbumNumber the number of album which is shown on the view.
   */
  public void setSelectedAlbumNumber(int selectedAlbumNumber) {
    this.selectedAlbumNumber = selectedAlbumNumber;
    this.selectedAlbum = data.getAlbums().get(this.selectedAlbumNumber - 1);
  }

  public String getTitle() {
    return selectedAlbum.title;
  }

  public void setTitle(String value) {
    selectedAlbum.title = value;
  }

  public String getArtist() {
    return selectedAlbum.artist;
  }

  /**
   * set the name of artist.
   *
   * @param value the name want artist to be.
   */
  public void setArtist(String value) {
    selectedAlbum.artist = value;
  }

  /**
   * Gets a boolean value which represents whether the album is classical.
   */
  public boolean getIsClassical() {
    return selectedAlbum.isClassical;
  }

  public void setIsClassical(boolean value) {
    selectedAlbum.isClassical = value;
  }

  public String getComposer() {
    return selectedAlbum.isClassical ? selectedAlbum.composer : "";
  }

  /**
   * Sets the name of composer when the album is classical.
   */
  public void setComposer(String value) {
    if (selectedAlbum.isClassical) {
      selectedAlbum.composer = value;
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
      result[i] = data.getAlbums().get(i).title;
    }
    return result;
  }


}
