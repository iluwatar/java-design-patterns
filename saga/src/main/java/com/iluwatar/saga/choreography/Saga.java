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

package com.iluwatar.saga.choreography;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Saga representation. Saga consists of chapters. Every ChoreographyChapter is executed a certain
 * service.
 */
public class Saga {

  private final List<Chapter> chapters;
  private int pos;
  private boolean forward;
  private boolean finished;


  public static Saga create() {
    return new Saga();
  }

  /**
   * get resuzlt of saga.
   *
   * @return result of saga @see {@link SagaResult}
   */
  public SagaResult getResult() {
    if (finished) {
      return forward
          ? SagaResult.FINISHED
          : SagaResult.ROLLBACKED;
    }

    return SagaResult.PROGRESS;
  }

  /**
   * add chapter to saga.
   *
   * @param name chapter name
   * @return this
   */
  public Saga chapter(String name) {
    this.chapters.add(new Chapter(name));
    return this;
  }

  /**
   * set value to last chapter.
   *
   * @param value invalue
   * @return this
   */
  public Saga setInValue(Object value) {
    if (chapters.isEmpty()) {
      return this;
    }
    chapters.get(chapters.size() - 1).setInValue(value);
    return this;
  }

  /**
   * get value from current chapter.
   *
   * @return value
   */
  public Object getCurrentValue() {
    return chapters.get(pos).getInValue();
  }

  /**
   * set value to current chapter.
   *
   * @param value to set
   */
  public void setCurrentValue(Object value) {
    chapters.get(pos).setInValue(value);
  }

  /**
   * set status for current chapter.
   *
   * @param result to set
   */
  public void setCurrentStatus(ChapterResult result) {
    chapters.get(pos).setResult(result);
  }

  void setFinished(boolean finished) {
    this.finished = finished;
  }

  boolean isForward() {
    return forward;
  }

  int forward() {
    return ++pos;
  }

  int back() {
    this.forward = false;
    return --pos;
  }


  private Saga() {
    this.chapters = new ArrayList<>();
    this.pos = 0;
    this.forward = true;
    this.finished = false;
  }

  Chapter getCurrent() {
    return chapters.get(pos);
  }


  boolean isPresent() {
    return pos >= 0 && pos < chapters.size();
  }

  boolean isCurrentSuccess() {
    return chapters.get(pos).isSuccess();
  }

  /**
   * Class presents a chapter status and incoming parameters(incoming parameter transforms to
   * outcoming parameter).
   */
  public static class Chapter {
    private final String name;
    private ChapterResult result;
    private Object inValue;


    public Chapter(String name) {
      this.name = name;
      this.result = ChapterResult.INIT;
    }

    public Object getInValue() {
      return inValue;
    }

    public void setInValue(Object object) {
      this.inValue = object;
    }

    public String getName() {
      return name;
    }

    /**
     * set result.
     *
     * @param result {@link ChapterResult}
     */
    public void setResult(ChapterResult result) {
      this.result = result;
    }

    /**
     * the result for chapter is good.
     *
     * @return true if is good otherwise bad
     */
    public boolean isSuccess() {
      return result == ChapterResult.SUCCESS;
    }
  }


  /**
   * result for chapter.
   */
  public enum ChapterResult {
    INIT, SUCCESS, ROLLBACK
  }

  /**
   * result for saga.
   */
  public enum SagaResult {
    PROGRESS, FINISHED, ROLLBACKED
  }

  @Override
  public String toString() {
    return "Saga{"
        + "chapters="
        + Arrays.toString(chapters.toArray())
        + ", pos="
        + pos
        + ", forward="
        + forward
        + '}';
  }
}
