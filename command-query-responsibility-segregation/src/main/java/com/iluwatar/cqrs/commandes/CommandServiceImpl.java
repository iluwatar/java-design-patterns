/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.cqrs.commandes;

import com.iluwatar.cqrs.domain.model.Author;
import com.iluwatar.cqrs.domain.model.Book;
import com.iluwatar.cqrs.util.HibernateUtil;
import org.hibernate.SessionFactory;

/**
 * This class is an implementation of {@link CommandService} interface. It uses Hibernate as an api
 * for persistence.
 */
public class CommandServiceImpl implements CommandService {

  private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  private Author getAuthorByUsername(String username) {
    Author author;
    try (var session = sessionFactory.openSession()) {
      var query = session.createQuery("from Author where username=:username");
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
    Book book;
    try (var session = sessionFactory.openSession()) {
      var query = session.createQuery("from Book where title=:title");
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
    var author = new Author(username, name, email);
    try (var session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.save(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void bookAddedToAuthor(String title, double price, String username) {
    var author = getAuthorByUsername(username);
    var book = new Book(title, price, author);
    try (var session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.save(book);
      session.getTransaction().commit();
    }
  }

  @Override
  public void authorNameUpdated(String username, String name) {
    var author = getAuthorByUsername(username);
    author.setName(name);
    try (var session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void authorUsernameUpdated(String oldUsername, String newUsername) {
    var author = getAuthorByUsername(oldUsername);
    author.setUsername(newUsername);
    try (var session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void authorEmailUpdated(String username, String email) {
    var author = getAuthorByUsername(username);
    author.setEmail(email);
    try (var session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void bookTitleUpdated(String oldTitle, String newTitle) {
    var book = getBookByTitle(oldTitle);
    book.setTitle(newTitle);
    try (var session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(book);
      session.getTransaction().commit();
    }
  }

  @Override
  public void bookPriceUpdated(String title, double price) {
    var book = getBookByTitle(title);
    book.setPrice(price);
    try (var session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(book);
      session.getTransaction().commit();
    }
  }

}
