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


import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


/**
 * The type App.
 * CS304 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1296
 */
@Slf4j
public class App {
  /**
   * The entry point of application.
   *
   * @param args the input arguments
   *
   * Initialize artist and store it in one linked list, initialize album and store it in another linked list.
   * Artist is the foreign key of the album, so you can access artist through the foreign key.
   */
  public static void main(String[] args) {
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
      LOGGER.info(albumList.get(i).getArtist().getName());
    }
  }
}
