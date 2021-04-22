package com.iluwatar.presentation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class ViewTest {
  String[] albumList = {"HQ", "The Rough Dancer and Cyclical Night", "The Black Light", "Symphony No.5"};

  @Test
  void testSave(){
    View view = new View();
    view.txtArtist.setText("testArtist");
    view.saveToPMod();
    assertEquals("testArtist", view.model.getArtist());
  }

  @Test
  void testLoad(){
    View view = new View();
    view.model.setSelectedAlbumNumber(2);
    view.loadFromPMod();
    assertEquals(albumList[1], view.model.getTitle());
  }
}
