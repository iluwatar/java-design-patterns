package com.iluwatar.presentation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ViewTest {
  String[] albumList = {"HQ", "The Rough Dancer and Cyclical Night", "The Black Light", "Symphony No.5"};

  @Test
  void testSave_setArtistAndTitle(){
    View view = new View();
    view.createView();
    String testTitle = "testTitle";
    String testArtist = "testArtist";
    view.getTxtArtist().setText(testArtist);
    view.getTxtTitle().setText(testTitle);
    view.saveToPMod();
    view.loadFromPMod();
    assertEquals(testTitle, view.getModel().getTitle());
    assertEquals(testArtist, view.getModel().getArtist());
  }

  @Test
  void testSave_setClassicalAndComposer(){
    View view = new View();
    view.createView();
    boolean isClassical = true;
    String testComposer = "testComposer";
    view.getChkClassical().setSelected(isClassical);
    view.getTxtComposer().setText(testComposer);
    view.saveToPMod();
    view.loadFromPMod();
    assertTrue(view.getModel().getIsClassical());
    assertEquals(testComposer, view.getModel().getComposer());
  }

  @Test
  void testLoad_1(){
    View view = new View();
    view.createView();
    view.getModel().setSelectedAlbumNumber(2);
    view.loadFromPMod();
    assertEquals(albumList[1], view.getModel().getTitle());
  }

  @Test
  void testLoad_2(){
    View view = new View();
    view.createView();
    view.getModel().setSelectedAlbumNumber(4);
    view.loadFromPMod();
    assertEquals(albumList[3], view.getModel().getTitle());
  }
}
