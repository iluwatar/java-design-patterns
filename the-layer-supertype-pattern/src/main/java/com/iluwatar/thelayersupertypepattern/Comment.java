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

package com.iluwatar.thelayersupertypepattern;


/**
 * Concrete comment
 */
public class Comment extends AbstractEntity implements CommentInterface {

  protected String content;
  protected String author;

  public Comment(String content, String author) {
    this.setContent(content);
    this.setAuthor(author);
  }

  @Override
  public void setId(String id) {
    if (this.getId() != null) {
      throw new IllegalArgumentException("The id for this comment has been set already.");
    }
    if (Integer.parseInt(id) < 1) {
      throw new IllegalArgumentException("The comment id must gte 1");
    }
    super.setId(id);
  }

  @Override
  public String getId() {
    return super.getId();
  }

  @Override
  public void setContent(String content) {
    if (content.length() < 2) {
      throw new IllegalArgumentException("The length of content must gte 2");
    }
    this.content = content;
  }

  @Override
  public String getContent() {
    return this.content;
  }

  @Override
  public void setAuthor(String author) {
    if (author.length() < 2) {
      throw new IllegalArgumentException("The length of author must gte 2");
    }
    this.author = author;
  }

  @Override
  public String getAuthor() {
    return this.author;
  }
}
