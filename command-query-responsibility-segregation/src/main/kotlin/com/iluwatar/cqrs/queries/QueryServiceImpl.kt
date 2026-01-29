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
// ABOUTME: Implementation of QueryService using Hibernate HQL and native queries.
// ABOUTME: Handles all read operations returning DTOs for authors and books.
package com.iluwatar.cqrs.queries

import com.iluwatar.cqrs.constants.AppConstants
import com.iluwatar.cqrs.dto.Author
import com.iluwatar.cqrs.dto.Book
import com.iluwatar.cqrs.util.HibernateUtil
import java.math.BigInteger

/**
 * This class is an implementation of [QueryService]. It uses Hibernate native queries to
 * return DTOs from the database.
 */
class QueryServiceImpl : QueryService {

    private val sessionFactory = HibernateUtil.sessionFactory

    override fun getAuthorByUsername(username: String): Author? {
        sessionFactory.openSession().use { session ->
            val sqlQuery = session.createQuery(
                "select new com.iluwatar.cqrs.dto.Author(a.name, a.email, a.username)" +
                    " from com.iluwatar.cqrs.domain.model.Author a where a.username=:username",
                Author::class.java
            )
            sqlQuery.setParameter(AppConstants.USER_NAME, username)
            return sqlQuery.uniqueResult()
        }
    }

    override fun getBook(title: String): Book? {
        sessionFactory.openSession().use { session ->
            val sqlQuery = session.createQuery(
                "select new com.iluwatar.cqrs.dto.Book(b.title, b.price)" +
                    " from com.iluwatar.cqrs.domain.model.Book b where b.title=:title",
                Book::class.java
            )
            sqlQuery.setParameter("title", title)
            return sqlQuery.uniqueResult()
        }
    }

    override fun getAuthorBooks(username: String): List<Book> {
        sessionFactory.openSession().use { session ->
            val sqlQuery = session.createQuery(
                "select new com.iluwatar.cqrs.dto.Book(b.title, b.price)" +
                    " from com.iluwatar.cqrs.domain.model.Author a, com.iluwatar.cqrs.domain.model.Book b " +
                    "where b.author.id = a.id and a.username=:username",
                Book::class.java
            )
            sqlQuery.setParameter(AppConstants.USER_NAME, username)
            return sqlQuery.list()
        }
    }

    override fun getAuthorBooksCount(username: String): BigInteger {
        sessionFactory.openSession().use { session ->
            val sqlQuery = session.createNativeQuery(
                "SELECT count(b.title)" +
                    " FROM  Book b, Author a" +
                    " where b.author_id = a.id and a.username=:username"
            )
            sqlQuery.setParameter(AppConstants.USER_NAME, username)
            return sqlQuery.uniqueResult() as BigInteger
        }
    }

    override fun getAuthorsCount(): BigInteger {
        sessionFactory.openSession().use { session ->
            val sqlQuery = session.createNativeQuery("SELECT count(id) from Author")
            return sqlQuery.uniqueResult() as BigInteger
        }
    }
}
