package com.iluwatar.thelayersupertypepattern;

import org.springframework.web.util.HtmlUtils;

/**
 * Concrete comment.
 */
class Comment implements CommentInterface {
  protected String id;
  protected String content;
  protected String author;

  public Comment(String content, String author) {
    this.setContent(content);
    this.setAuthor(author);
  }

  public void setId(String id) {
    if (this.id != null) {
      throw new IllegalArgumentException(
              "The ID for this comment has been set already.");
    }
    if (Integer.valueOf(this.id) < 1) {
      throw new IllegalArgumentException(
              "The comment ID is invalid.");
    }
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setContent(String content) {
    if (content.length() < 2) {
      throw new IllegalArgumentException(
              "The content of the comment is invalid.");
    }
    this.content = HtmlUtils.htmlEscape(content.trim());
  }

  public String getContent() {
    return this.content;
  }

  public void setAuthor(String author) {
    if (author.length() < 2) {
      throw new IllegalArgumentException(
              "The author is invalid.");
    }
    this.author = author;
  }

  public String getAuthor() {
    return this.author;
  }
}
