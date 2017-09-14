/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Gopinath Langote
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
  private String id;
  private String title;
  private Long length;
  private String description;
  private String publisher;
  private String status;

  /**
   * @param id          video unique id
   * @param title       video title
   * @param length      video length in seconds
   * @param description video description by publisher
   * @param publisher   video publisher name
   * @param status      video status {private, public}
   */
  public Video(String id, String title, Long length, String description, String publisher, String status) {
    this.id = id;
    this.title = title;
    this.length = length;
    this.description = description;
    this.publisher = publisher;
    this.status = status;
  }

  @Override
  public String toString() {
    return "Video{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", length=" + length +
        ", description='" + description + '\'' +
        ", publisher='" + publisher + '\'' +
        ", status='" + status + '\'' +
        '}';
  }
}
