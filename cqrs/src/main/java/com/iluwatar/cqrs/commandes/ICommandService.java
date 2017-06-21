package com.iluwatar.cqrs.commandes;

public interface ICommandService {

  public abstract void authorCreated(String username, String name, String email);

  public abstract void bookAddedToAuthor(String title, double price, String username);

  public abstract void authorNameUpdated(String username, String name);

  public abstract void authorUsernameUpdated(String oldUsername, String newUsername);

  public abstract void authorEmailUpdated(String username, String email);

  public abstract void bookTitleUpdated(String oldTitle, String newTitle);

  public abstract void bookPriceUpdated(String title, double price);

}
