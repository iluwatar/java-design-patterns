/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.saga.orchestration;

import java.util.ArrayList;
import java.util.List;

/**
 * Saga representation.
 * Saga consists of chapters.
 * Every ChoreographyChapter is executed by a certain service.
 */
public class Saga {

  private List<Chapter> chapters;


  private Saga() {
    this.chapters = new ArrayList<>();
  }


  public Saga chapter(String name) {
    this.chapters.add(new Chapter(name));
    return this;
  }


  public Chapter get(int idx) {
    return chapters.get(idx);
  }

  public boolean isPresent(int idx) {
    return idx >= 0 && idx < chapters.size();
  }


  public static Saga create() {
    return new Saga();
  }

  /**
   * result for saga.
   */
  public enum Result {
    FINISHED, ROLLBACK, CRASHED
  }

  /**
   * class represents chapter name.
   */
  public static class Chapter {
    String name;

    public Chapter(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
