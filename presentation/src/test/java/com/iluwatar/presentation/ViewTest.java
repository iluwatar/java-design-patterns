package com.iluwatar.presentation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
class ViewTest {
  String[] albumList = {"HQ", "The Rough Dancer and Cyclical Night", "The Black Light", "Symphony No.5"};

  @Test
  void testSave_setArtist(){
    View view = new View();
    view.createView();
    view.txtArtist.setText("testArtist");
    view.saveToPMod();
    assertEquals("testArtist", view.model.getArtist());
  }

  @Test
  void testSave_setTitle(){
    View view = new View();
    view.createView();
    String testTitle = "testTitle";
    view.txtTitle.setText(testTitle);
    view.saveToPMod();
    assertEquals(testTitle, view.model.getTitle());
  }

  @Test
  void testLoad_1(){
    View view = new View();
    view.createView();
    view.model.setSelectedAlbumNumber(2);
    view.loadFromPMod();
    assertEquals(albumList[1], view.model.getTitle());
  }

  @Test
  void testLoad_2(){
    View view = new View();
    view.createView();
    view.model.setSelectedAlbumNumber(4);
    view.loadFromPMod();
    assertEquals(albumList[3], view.model.getTitle());
  }
}
