/*
 * This project is licensed under the MIT license.
 * Module model-view-viewmodel is using ZK framework
 * licensed under LGPL (see lgpl-3.0.txt).
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
package com.iluwatar.denpendentmapping;

import com.iluwatar.denpendentmapping.structure.DependentObj;
import com.iluwatar.denpendentmapping.structure.MasterObj;
import java.util.ArrayList;
import java.util.List;

/**
 * An album class of which the instance have a list of tracks.
 */
public class Album extends MasterObj {

  /**
   * tracks of album.
   */
  private List<Track> tracks = new ArrayList<>();

  /**
   * id of album.
   */
  private Long id;

  /**
   * construction method.
   *
   * @param newid    id of album.
   * @param newtitle title of album.
   */
  public Album(final Long newid, final String newtitle) {
    super(newtitle);
    this.id = newid;
  }

  /**
   * Add track into album.
   *
   * @param obj the specific trac you want add into album.
   */
  @Override
  public void addDepObj(final DependentObj obj) {
    tracks.add((Track) obj);
  }

  /**
   * remove specific track of album.
   *
   * @param obj the specific track you want remove.
   */
  @Override
  public void removeDepObj(final DependentObj obj) {
    tracks.remove((Track) obj);
  }

  /**
   * remove the i-th track in album.
   *
   * @param i the index of track.
   */
  @Override
  public void removeDepObj(final int i) {
    tracks.remove(i);
  }

  /**
   * get the dependent class instances as an form of Dependent class array.
   *
   * @return the array of dependent instances.
   */
  @Override
  public DependentObj[] getDepObjs() {
    return tracks.toArray(new DependentObj[tracks.size()]);
  }

  /**
   * get the tracks of album.
   *
   * @return the list of tracks.
   */
  public Track[] getTracks() {
    return tracks.toArray(new Track[tracks.size()]);
  }

  /**
   * get the id of album.
   *
   * @return the id of album.
   */
  public Long getId() {
    return id;
  }
}
