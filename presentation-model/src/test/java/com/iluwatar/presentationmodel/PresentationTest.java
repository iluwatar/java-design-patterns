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

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PresentationTest {
  String[] albumList = {"HQ", "The Rough Dancer and Cyclical Night", "The Black Light", "Symphony No.5"};

  @Test
  void testCreateAlbumList() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    String[] list = model.getAlbumList();
    assertEquals(Arrays.toString(albumList), Arrays.toString(list));
  }

  @Test
  void testSetSelectedAlbumNumber_1() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    final int selectId = 2;
    model.setSelectedAlbumNumber(selectId);
    assertEquals(albumList[selectId - 1], model.getTitle());
  }

  @Test
  void testSetSelectedAlbumNumber_2() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    final int selectId = 4;
    model.setSelectedAlbumNumber(selectId);
    assertEquals(albumList[selectId - 1], model.getTitle());
  }

  @Test
  void testSetTitle_1() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    String testTitle = "TestTile";
    model.setTitle(testTitle);
    assertEquals(testTitle, model.getTitle());
  }

  @Test
  void testSetTitle_2() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    String testTitle = "";
    model.setTitle(testTitle);
    assertEquals(testTitle, model.getTitle());
  }

  @Test
  void testSetArtist_1() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    String testArtist = "TestArtist";
    model.setArtist(testArtist);
    assertEquals(testArtist, model.getArtist());
  }

  @Test
  void testSetArtist_2() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    String testArtist = "";
    model.setArtist(testArtist);
    assertEquals(testArtist, model.getArtist());
  }

  @Test
  void testSetIsClassical() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    model.setIsClassical(true);
    assertTrue(model.getIsClassical());
  }

  @Test
  void testSetComposer_false() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    String testComposer = "TestComposer";

    model.setIsClassical(false);
    model.setComposer(testComposer);
    assertEquals("", model.getComposer());
  }

  @Test
  void testSetComposer_true() {
    PresentationModel model = new PresentationModel(PresentationModel.albumDataSet());
    String testComposer = "TestComposer";

    model.setIsClassical(true);
    model.setComposer(testComposer);
    assertEquals(testComposer, model.getComposer());
  }
}
