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

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * The process of creating a model supertype, can be viewed as a mixture
 * of a few atomic and granular refactoring techniques, such as Extract SuperClass,
 * Pull Up Field and Pull Up Method
 * For the public part extracted by the superclass,
 * the subclass can also implement a specific implementation of the methods of the public part
 */
@Slf4j
public class App {

  /**
   * @param args command line args
   */
  public static void main(String[] args) {
    PostInterface post = new Post("A sample post.", "This is the content of the post.");
    List<CommentInterface> commentInterfaces = new ArrayList<>();
    Comment comment1 = new Comment("One banal comment for the previous post.", "A fictional commenter");
    commentInterfaces.add(comment1);
    Comment comment2 = new Comment("Yet another banal comment for the previous post.", "A fictional commenter");
    commentInterfaces.add(comment2);
    post.setComments(commentInterfaces);

    LOGGER.info(post.getTitle() + ":" + post.getContent());
    for (CommentInterface commentInterface : post.getComments()) {
      LOGGER.info(commentInterface.getAuthor() + ":" +  commentInterface.getContent());
    }
  }
}
