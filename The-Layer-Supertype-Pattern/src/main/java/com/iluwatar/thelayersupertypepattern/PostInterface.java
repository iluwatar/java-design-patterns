package com.iluwatar.thelayersupertypepattern;

import java.util.ArrayList;

/**
 * Interface.
 */
public interface PostInterface {
  public void setId(String id);

  public String getId();

  public void setTitle(String title);

  public String getTitle();

  public void setContent(String content);

  public String getContent();

  public void setComment(CommentInterface comment);

  public void setComments(CommentInterface[] comments);

  public ArrayList<CommentInterface> getComments();
}
