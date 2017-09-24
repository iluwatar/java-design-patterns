/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 Gopinath Langote
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.iluwatar.partialresponse;

/**
 * {@link Video} is a entity to serve from server.It contains all video related information..
 * <p>
 */
public class Video {
  private final Integer id;
  private final String title;
  private final Integer length;
  private final String description;
  private final String director;
  private final String language;

  /**
   * @param id          video unique id
   * @param title       video title
   * @param length      video length in minutes
   * @param description video description by publisher
   * @param director    video director name
   * @param language    video language {private, public}
   */
  public Video(Integer id, String title, Integer length, String description, String director, String language) {
    this.id = id;
    this.title = title;
    this.length = length;
    this.description = description;
    this.director = director;
    this.language = language;
  }

  /**
   * @return json representaion of video
   */
  @Override
  public String toString() {
    return "{"
        + "\"id\": " + id + ","
        + "\"title\": \"" + title + "\","
        + "\"length\": " + length + ","
        + "\"description\": \"" + description + "\","
        + "\"director\": \"" + director + "\","
        + "\"language\": \"" + language + "\","
        + "}";
  }
}
