package com.iluwater.functionalcoreimperativeshell;

import com.iluwatar.functionalcoreimperativeshell.*;
import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * Shell Tester.
 *
 * @author inkfin
 * @since <pre>May 15, 2022</pre>
 * @version 1.0
 */
public class ShellTest {

  /**
   *
   * Method: reviewArticle(DraftArticle draftArticle)
   *
   */
  @Test
  public void testReviewArticleYes() {
    Article article = new DraftArticle(
       "The Game Awards crowns The Legend of Zelda...",
       "The Game Awards 2017 The 17 biggest trailers and announcements..."
    );

    article = Shell.reviewArticle((DraftArticle) article, "y");

    assertEquals(article.getClass(), PublishArticle.class);
    assertEquals(article.getTitle(), "The Game Awards crowns The Legend of Zelda...");
    assertEquals(article.getBody(), "The Game Awards 2017 The 17 biggest trailers and announcements...");
  }

  /**
   *
   * Method: reviewArticle(DraftArticle draftArticle)
   *
   */
  @Test
  public void testReviewArticleNo() {
    Article article = new DraftArticle(
       "The Game Awards crowns The Legend of Zelda...",
       "The Game Awards 2017 The 17 biggest trailers and announcements..."
    );

    article = Shell.reviewArticle((DraftArticle) article, "n");

    assertEquals(article.getClass(), DraftArticle.class);
    assertEquals(article.getTitle(), "The Game Awards crowns The Legend of Zelda...");
    assertEquals(article.getBody(), "The Game Awards 2017 The 17 biggest trailers and announcements...");
  }

} 
