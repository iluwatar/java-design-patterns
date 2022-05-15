package com.iluwatar.functionalcoreimperativeshell;

public class App {

    public static void main(String[] args) {
        /*
         * This is a brief example of how the functional core imperative shell design pattern works.
         * Purely functional code only calls functions to get immutable values, no values will be changed
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

        System.out.println("Create Draft...");

        Article article = Core.createDraft(
                "The Game Awards crowns The Legend of Zelda...",
                "The Game Awards 2017 The 17 biggest trailers and announcements..."
        );

        System.out.println(article);

        System.out.println("Publish Draft");

        article = Core.publishDraft((DraftArticle) article);

        System.out.println(article);

        System.out.println("Revert Publish");

        article = Core.revertPublish((PublishArticle) article);

        System.out.println(article);

        System.out.println("Call user to review draft");

        article = Shell.reviewArticle((DraftArticle) article);

        System.out.println(article);
    }
}