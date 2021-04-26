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

/**
 * The type Album test.
 * CS304 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1296
 */
public class AlbumTest {
  /**
   * Album test.
   */
  @Test
  public void ClassTest() {
    Artist temp = new Artist(1, "artist");
    Artist temp2 = new Artist(1, "artist");
    String t = "artist" + 1;
    temp.setName(t);
    Album temp1 = new Album(1, "album", temp);
    Assert.assertEquals("artist1", temp1.getArtist().getName());
    Assert.assertEquals("album", temp1.getTitle());
    String t1 = "Album" + 1;
    temp1.setTitle(t1);
    Assert.assertEquals("Album1", temp1.getTitle());
    temp1.setArtist(temp2);
    Assert.assertEquals("artist", temp1.getArtist().getName());
  }
}
