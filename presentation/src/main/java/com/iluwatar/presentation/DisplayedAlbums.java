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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * a class used to deal with albums.
 *
 */
@Slf4j
@Getter
public class DisplayedAlbums {
  /**
   * albums a list of albums.
   */
  private final List<Album> albums;

  /**
   * a constructor method.
   */
  public DisplayedAlbums() {
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
