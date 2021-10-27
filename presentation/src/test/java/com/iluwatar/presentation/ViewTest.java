/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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
