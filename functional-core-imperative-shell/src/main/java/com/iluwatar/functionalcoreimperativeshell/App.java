package com.iluwatar.functionalcoreimperativeshell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The static main class.
 */
public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * The main class.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    /*
     * This is a brief example of how the functional core imperative shell design pattern works.
     * Purely functional code only calls functions to get immutable values,
     * no values will be changed
     * in this process, which makes it easy to understand.
     *
     * In brief:
     * The object values don't change: they just create new values.
     *
     * However, in order to deal with mutable values, like user input and database, new methods are
     * needed. This design pattern shows that we can use a helper class to deal with these stuff.
     *
     * In this program, the functional core is surrounded by an imperative shell, which handles the
     * std input.
     */

    LOGGER.info("Create Draft...");

    Article article = Core.createDraft(
        "The Game Awards crowns The Legend of Zelda...",
        "The Game Awards 2017 The 17 biggest trailers and announcements..."
    );

    LOGGER.info(String.valueOf(article));

    LOGGER.info("Publish Draft");

    article = Core.publishDraft((DraftArticle) article);

    LOGGER.info(String.valueOf(article));

    LOGGER.info("Revert Publish");

    article = Core.revertPublish((PublishArticle) article);

    LOGGER.info(String.valueOf(article));

    LOGGER.info("Call user to review draft");

    article = Shell.reviewArticle((DraftArticle) article, "y");

    LOGGER.info(String.valueOf(article));
  }
}