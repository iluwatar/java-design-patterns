package com.iluwatar.thelayersupertypepattern;

import java.util.ArrayList;

import org.springframework.web.util.HtmlUtils;


/**
 * Post is the concret class.
 */
public class Post implements PostInterface {
  protected String id;
  protected String title;
  protected String content;
  protected ArrayList<CommentInterface> comments = new ArrayList<>();

  //protected com.iluwatar.thelayersupertypepattern.CommentInterface[] comments;

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
  public void setId(String id) {
    if (this.id != null) {
      throw new IllegalArgumentException("The ID for this post has been set already.");
    }
    if (Integer.valueOf(id) < 1) {
      throw new IllegalArgumentException(
              "The post ID is invalid.");
    }
    this.id = String.valueOf(id);
  }

  /**
   * get.
   *
   * @return id.
   */
  public String getId() {
    return this.id;
  }

  /**
   * title.
   *
   * @param title title.
   */
  public void setTitle(String title) {
    if (title.length() < 2
            || title.length() > 100) {
      throw new IllegalArgumentException(
              "The post title is invalid.");
    }
    this.title = HtmlUtils.htmlEscape(title.trim());
  }

  /**
   * get title.
   *
   * @return title.
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * set content.
   *
   * @param content content.
   */
  public void setContent(String content) {
    if (content.length() < 2) {
      throw new IllegalArgumentException(
              "The post content is invalid.");
    }
    this.content = HtmlUtils.htmlEscape(content.trim());

  }

  /**
   * get content.
   *
   * @return content.
   */
  public String getContent() {
    return this.content;
  }

  /**
   * set comment.
   *
   * @param comment comment.
   */
  public void setComment(CommentInterface comment) {
    this.comments.add(comment);
  }

  /**
   * setcomment.
   *
   * @param comments comments.
   */
  public void setComments(CommentInterface[] comments) {
    for (CommentInterface comment : comments) {
      this.setComment(comment);
    }
  }

  /**
   * getcomments.
   *
   * @return comments.
   */
  public ArrayList<CommentInterface> getComments() {
    return this.comments;
  }
}