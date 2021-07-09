package com.iluwatar.thelayersupertypepattern;

/**
 * main.
 */
public class App {
  /**
   * main.
   *
   * @param args main.
   */
  public static void main(String[] args) {
    PostInterface post = new Post("A sample post.",
            "This is the content of the post.");
    CommentInterface[] commentInterfaces = new CommentInterface[2];
    commentInterfaces[0] = new Comment("One banal comment for the previous post.",
            "A fictional commenter");
    commentInterfaces[1] = new Comment("Yet another banal comment for the previous post.",
            "A fictional commenter");
    post.setComments(commentInterfaces);

    System.out.println(post.getTitle() + post.getTitle());
    for (CommentInterface commentInterface : post.getComments()) {
      System.out.println(commentInterface.getAuthor() + commentInterface.getContent());
    }
  }
}
