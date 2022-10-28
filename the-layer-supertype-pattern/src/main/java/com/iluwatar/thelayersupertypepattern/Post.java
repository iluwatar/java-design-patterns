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


import java.util.ArrayList;
import java.util.List;


/**
 * Post  class..
 */
public class Post extends AbstractEntity implements PostInterface {

  protected String title;
  protected String content;
  protected List<CommentInterface> comments = new ArrayList<>();

  /**
   * Post.
   *
   * @param title title
   * @param content content
   */
  public Post(String title, String content) {
    this.setTitle(title);
    this.setContent(content);
  }

  /**
   * set method.
   *
   * @param id id
   */

  /**
   * @param title title.
   */
  @Override
  public void setTitle(String title) {
    if (title.length() < 2
            || title.length() > 100) {
      throw new IllegalArgumentException(
              "The post title is invalid.");
    }
    this.title = title;
  }

  /**
   * @return title.
   */
  @Override
  public String getTitle() {
    return this.title;
  }

  /**
   * @param content content.
   */
  @Override
  public void setContent(String content) {
    if (content.length() < 2) {
      throw new IllegalArgumentException(
              "The post content is invalid.");
    }
    this.content = content;

  }

  /**
   * @return content.
   */
  @Override
  public String getContent() {
    return this.content;
  }

  /**
   * @param comment comment.
   */
  @Override
  public void setComment(CommentInterface comment) {
    this.comments.add(comment);
  }

  /**
   * @param comments comments.
   */
  @Override
  public void setComments(List<CommentInterface> comments) {
    if(comments == null || comments.size() < 1) {
      throw new IllegalArgumentException("The length of comments must gte 1");
    }
    this.comments = comments;
  }

  /**
   * @return comments.
   */
  @Override
  public List<CommentInterface> getComments() {
    return this.comments;
  }
}