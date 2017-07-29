package com.iluwatar.cqrs.commandes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.iluwatar.cqrs.domain.model.Author;
import com.iluwatar.cqrs.domain.model.Book;
import com.iluwatar.cqrs.util.HibernateUtil;

/**
 * This class is an implementation of {@link ICommandService} interface. It uses Hibernate as an api for persistence.
 *
 */
public class CommandServiceImpl implements ICommandService {

  private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  private Author getAuthorByUsername(String username) {
    Author author = null;
    try (Session session = sessionFactory.openSession()) {
      Query query = session.createQuery("from Author where username=:username");
      query.setParameter("username", username);
      author = (Author) query.uniqueResult();
    }
    if (author == null) {
      HibernateUtil.getSessionFactory().close();
      throw new NullPointerException("Author " + username + " doesn't exist!");
    }
    return author;
  }

  private Book getBookByTitle(String title) {
    Book book = null;
    try (Session session = sessionFactory.openSession()) {
      Query query = session.createQuery("from Book where title=:title");
      query.setParameter("title", title);
      book = (Book) query.uniqueResult();
    }
    if (book == null) {
      HibernateUtil.getSessionFactory().close();
      throw new NullPointerException("Book " + title + " doesn't exist!");
    }
    return book;
  }

  @Override
  public void authorCreated(String username, String name, String email) {
    Author author = new Author(username, name, email);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.save(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void bookAddedToAuthor(String title, double price, String username) {
    Author author = getAuthorByUsername(username);
    Book book = new Book(title, price, author);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.save(book);
      session.getTransaction().commit();
    }
  }

  @Override
  public void authorNameUpdated(String username, String name) {
    Author author = getAuthorByUsername(username);
    author.setName(name);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void authorUsernameUpdated(String oldUsername, String newUsername) {
    Author author = getAuthorByUsername(oldUsername);
    author.setUsername(newUsername);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void authorEmailUpdated(String username, String email) {
    Author author = getAuthorByUsername(username);
    author.setEmail(email);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void bookTitleUpdated(String oldTitle, String newTitle) {
    Book book = getBookByTitle(oldTitle);
    book.setTitle(newTitle);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(book);
      session.getTransaction().commit();
    }
  }

  @Override
  public void bookPriceUpdated(String title, double price) {
    Book book = getBookByTitle(title);
    book.setPrice(price);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(book);
      session.getTransaction().commit();
    }
  }

}
