package com.iluwatar.functionalcoreimperativeshell;

/**
 * Deal with the immutable classes.
 * The objects in this class are immutable for callers, and won't affect anything outside.
 */
public class Core {

  /**
   * create a draftArticle.
   *
   * @param title title of the draft article
   * @param body body of the draft article
   * @return new draft article
   */
  public static DraftArticle createDraft(String title, String body) {
    return new DraftArticle(title, body);
  }

  /**
   * change the draft article to publish article.
   *
   * @param draftArticle the draft article
   * @return new publish article
   */
  public static PublishArticle publishDraft(DraftArticle draftArticle) {
    return new PublishArticle(draftArticle.getTitle(), draftArticle.getBody());
  }

  /**
   * revert the publish article to draft article.
   *
   * @param publishArticle the publish article
   * @return new draft article
   */
  public static DraftArticle revertPublish(PublishArticle publishArticle) {
    return new DraftArticle(publishArticle.getTitle(), publishArticle.getBody());
  }
}
