package com.iluwatar.functionalcoreimperativeshell;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

/**
 * The Shell Class deal with all the user inputs.
 */
public class Shell {

  /**
   * review the Article & get user input.
   *
   * @param draftArticle draft to be reviewed
   * @return new PublishArticle if success, DraftArticle if failed.
   */
  public static Article reviewArticle(DraftArticle draftArticle) {
    Scanner input = new Scanner(System.in, StandardCharsets.US_ASCII);
    String key;
    do {
      System.out.println("Publish this draft? (y, n)");
      key = input.nextLine();
    } while (!Objects.equals(key, "y") && !Objects.equals(key, "n"));

    if (Objects.equals(key, "y")) {
      return Core.publishDraft(draftArticle);
    } else {
      return draftArticle;
    }
  }
}
