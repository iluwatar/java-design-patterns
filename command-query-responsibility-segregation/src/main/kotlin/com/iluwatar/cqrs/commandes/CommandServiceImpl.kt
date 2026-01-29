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
// ABOUTME: Implementation of CommandService using Hibernate for persistence.
// ABOUTME: Handles all write operations for authors and books in the CQRS pattern.
package com.iluwatar.cqrs.commandes

import com.iluwatar.cqrs.domain.model.Author
import com.iluwatar.cqrs.domain.model.Book
import com.iluwatar.cqrs.util.HibernateUtil

/**
 * This class is an implementation of [CommandService] interface. It uses Hibernate as an api
 * for persistence.
 */
class CommandServiceImpl : CommandService {

    private val sessionFactory = HibernateUtil.sessionFactory

    private fun getAuthorByUsername(username: String): Author {
        val author: Author?
        sessionFactory.openSession().use { session ->
            val query = session.createQuery("from Author where username=:username", Author::class.java)
            query.setParameter("username", username)
            author = query.uniqueResult()
        }
        if (author == null) {
            HibernateUtil.sessionFactory.close()
            throw NullPointerException("Author $username doesn't exist!")
        }
        return author
    }

    private fun getBookByTitle(title: String): Book {
        val book: Book?
        sessionFactory.openSession().use { session ->
            val query = session.createQuery("from Book where title=:title", Book::class.java)
            query.setParameter("title", title)
            book = query.uniqueResult()
        }
        if (book == null) {
            HibernateUtil.sessionFactory.close()
            throw NullPointerException("Book $title doesn't exist!")
        }
        return book
    }

    override fun authorCreated(username: String, name: String, email: String) {
        val author = Author(username, name, email)
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(author)
            session.transaction.commit()
        }
    }

    override fun bookAddedToAuthor(title: String, price: Double, username: String) {
        val author = getAuthorByUsername(username)
        val book = Book(title, price, author)
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(book)
            session.transaction.commit()
        }
    }

    override fun authorNameUpdated(username: String, name: String) {
        val author = getAuthorByUsername(username)
        author.name = name
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.update(author)
            session.transaction.commit()
        }
    }

    override fun authorUsernameUpdated(oldUsername: String, newUsername: String) {
        val author = getAuthorByUsername(oldUsername)
        author.username = newUsername
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.update(author)
            session.transaction.commit()
        }
    }

    override fun authorEmailUpdated(username: String, email: String) {
        val author = getAuthorByUsername(username)
        author.email = email
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.update(author)
            session.transaction.commit()
        }
    }

    override fun bookTitleUpdated(oldTitle: String, newTitle: String) {
        val book = getBookByTitle(oldTitle)
        book.title = newTitle
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.update(book)
            session.transaction.commit()
        }
    }

    override fun bookPriceUpdated(title: String, price: Double) {
        val book = getBookByTitle(title)
        book.price = price
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.update(book)
            session.transaction.commit()
        }
    }
}
