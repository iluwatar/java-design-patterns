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
// ABOUTME: Integration tests for CQRS pattern verifying CommandService and QueryService interactions.
// ABOUTME: Tests author/book CRUD operations and query functionality with H2 in-memory database.
package com.iluwatar.cqrs

import com.iluwatar.cqrs.commandes.CommandServiceImpl
import com.iluwatar.cqrs.dto.Author
import com.iluwatar.cqrs.dto.Book
import com.iluwatar.cqrs.queries.QueryService
import com.iluwatar.cqrs.queries.QueryServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.math.BigInteger

/**
 * Integration test of IQueryService and ICommandService with h2 data
 */
class IntegrationTest {

    companion object {
        private lateinit var queryService: QueryService

        @BeforeAll
        @JvmStatic
        fun initializeAndPopulateDatabase() {
            val commandService = CommandServiceImpl()
            queryService = QueryServiceImpl()

            // create first author1
            commandService.authorCreated("username1", "name1", "email1")

            // create author1 and update all its data
            commandService.authorCreated("username2", "name2", "email2")
            commandService.authorEmailUpdated("username2", "new_email2")
            commandService.authorNameUpdated("username2", "new_name2")
            commandService.authorUsernameUpdated("username2", "new_username2")

            // add book1 to author1
            commandService.bookAddedToAuthor("title1", 10.0, "username1")

            // add book2 to author1 and update all its data
            commandService.bookAddedToAuthor("title2", 20.0, "username1")
            commandService.bookPriceUpdated("title2", 30.0)
            commandService.bookTitleUpdated("title2", "new_title2")
        }
    }

    @Test
    fun testGetAuthorByUsername() {
        val author = queryService.getAuthorByUsername("username1")
        assertEquals("username1", author?.username)
        assertEquals("name1", author?.name)
        assertEquals("email1", author?.email)
    }

    @Test
    fun testGetUpdatedAuthorByUsername() {
        val author = queryService.getAuthorByUsername("new_username2")
        val expectedAuthor = Author("new_name2", "new_email2", "new_username2")
        assertEquals(expectedAuthor, author)
    }

    @Test
    fun testGetBook() {
        val book = queryService.getBook("title1")
        assertEquals("title1", book?.title)
        assertEquals(10.0, book?.price ?: 0.0, 0.01)
    }

    @Test
    fun testGetAuthorBooks() {
        val books = queryService.getAuthorBooks("username1")
        assertEquals(2, books.size)
        assertTrue(books.contains(Book("title1", 10.0)))
        assertTrue(books.contains(Book("new_title2", 30.0)))
    }

    @Test
    fun testGetAuthorBooksCount() {
        val bookCount = queryService.getAuthorBooksCount("username1")
        assertEquals(BigInteger("2"), bookCount)
    }

    @Test
    fun testGetAuthorsCount() {
        val authorCount = queryService.getAuthorsCount()
        assertEquals(BigInteger("2"), authorCount)
    }
}
