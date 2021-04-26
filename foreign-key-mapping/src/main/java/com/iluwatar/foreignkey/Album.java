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



/**
 * The type Album.
 * CS304 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1296
 */

class Album {
  private long id;
  private String title;
  private Artist artist;

  /**
   * Instantiates a new Album.
   *
   * @param id     the id
   * @param title  the title
   * @param artist the artist
   */
  public Album(long id, String title, Artist artist) {
    this.id = id;
    this.title = title;
    this.artist = artist;
  }

  /**
   * Gets title.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets title.
   *
   * @param title the title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets artist.
   *
   * @return the artist
   */
  public Artist getArtist() {
    return artist;
  }

  /**
   * Sets artist.
   *
   * @param artist the artist
   */
  public void setArtist(Artist artist) {
    this.artist = artist;
  }
}