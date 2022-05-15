package com.iluwatar.functionalcoreimperativeshell;

/**
 * immutable draft article
 */
public class DraftArticle extends Article{
    public DraftArticle(String title, String body) {
        super(title, body);
    }

    @Override
    public String toString() {
        return "DraftArticle{" +
                "title='" + this.getTitle() + '\'' +
                ", body='" + this.getBody() + '\'' +
                '}';
    }
}
