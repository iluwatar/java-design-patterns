package com.iluwatar.functionalcoreimperativeshell;

import java.util.Objects;

/**
 * The Shell Class deal with all the user inputs.
 */
public class Shell {

  private Shell() {}

  /**
   * review the Article & get user input.
   *
   * @param draftArticle draft to be reviewed
   * @param key 'y' for yes, otherwise no, simulate user inputs
   * @return new PublishArticle if success, DraftArticle if failed.
   */
  public static Article reviewArticle(DraftArticle draftArticle, String key) {
    key = key.toLowerCase();

    if (Objects.equals(key, "y")) {
      return Core.publishDraft(draftArticle);
    } else {
      return draftArticle;
    }
  }
}
