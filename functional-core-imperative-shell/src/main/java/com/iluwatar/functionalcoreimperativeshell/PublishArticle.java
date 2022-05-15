package com.iluwatar.functionalcoreimperativeshell;

/**
 * immutable published article
 */
public class PublishArticle extends Article{
    public PublishArticle(String title, String body) {
        super(title, body);
    }

    @Override
    public String toString() {
        return "PublishArticle{" +
                "title='" + this.getTitle() + '\'' +
                ", body='" + this.getBody() + '\'' +
                '}';
    }
}
