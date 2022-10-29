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
package com.iluwatar.dependentmapping.structure;

/**
 * A master class of which instance will be depended
 * by a list of DependentObj instances.
 */
public abstract class MasterObj {
  /**
   * title.
   */
  private String title;

  /**
   * add new dependent instance.
   *
   * @param obj the dependent instance to be added.
   */
  public abstract void addDepObj(DependentObj obj);

  /**
   * remove specific dependent instance.
   *
   * @param obj the dependent instance to be removed.
   */
  public abstract void removeDepObj(DependentObj obj);

  /**
   * remove i-th dependent instance.
   *
   * @param i the index of instance to be removed.
   */
  public abstract void removeDepObj(int i);

  /**
   * get the list dependent instances.
   *
   * @return the list.
   */
  public abstract DependentObj[] getDepObjs();

  /**
   * construction method.
   *
   * @param newTitle title.
   */
  public MasterObj(final String newTitle) {
    this.title = newTitle;
  }

  /**
   * get title of the master instance.
   *
   * @return title.
   */
  public String getTitle() {
    return title;
  }
}
