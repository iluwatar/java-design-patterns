package com.iluwatar.functionalcoreimperativeshell;

/**
 * immutable class.
 */
public abstract class Article {
    private final String title;
    private final String body;

    public Article(String title, String body) {
        this.title = title;
        this.body  = body ;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
