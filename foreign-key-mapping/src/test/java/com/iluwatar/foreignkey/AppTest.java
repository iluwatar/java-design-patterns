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

package com.iluwatar.foreignkey;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * The type App test.
 */
public class AppTest {
  /**
   * App test.
   */
  @Test
  public void AppTest() {
    List<Artist> artistList = new LinkedList<Artist>();
    List<Album> albumList = new LinkedList<Album>();
    for (int i = 0; i < 5; i++) {
      Artist temp = new Artist(i, "artist");
      String t = "artist" + Integer.toString(i);
      temp.setName(t);
      artistList.add(temp);
    }
    for (int i = 0; i < 5; i++) {
      Album temp = new Album(i, "artist", artistList.get(i));
      albumList.add(temp);
    }

    Assert.assertEquals("artist0", albumList.get(0).getArtist().getName());
    Assert.assertEquals("artist1", albumList.get(1).getArtist().getName());
    Assert.assertEquals("artist2", albumList.get(2).getArtist().getName());
    Assert.assertEquals("artist3", albumList.get(3).getArtist().getName());
    Assert.assertEquals("artist4", albumList.get(4).getArtist().getName());

  }
}
