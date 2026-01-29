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
package com.iluwatar.dao

// ABOUTME: Data Access Object interface defining CRUD operations for Customer entities.
// ABOUTME: Provides abstraction over persistence mechanism (in-memory or database).

import java.util.stream.Stream

/**
 * In an application the Data Access Object (DAO) is a part of Data access layer. It is an object
 * that provides an interface to some type of persistence mechanism. By mapping application calls to
 * the persistence layer, DAO provides some specific data operations without exposing details of the
 * database. This isolation supports the Single responsibility principle. It separates what data
 * accesses the application needs, in terms of domain-specific objects and data types (the public
 * interface of the DAO), from how these needs can be satisfied with a specific DBMS, database
 * schema, etc.
 *
 * Any change in the way data is stored and retrieved will not change the client code as the
 * client will be using interface and need not worry about exact source.
 *
 * @see InMemoryCustomerDao
 * @see DbCustomerDao
 */
interface CustomerDao {

    /**
     * Get all customers.
     *
     * @return all the customers as a stream. The stream may be lazily or eagerly evaluated based on
     *     the implementation. The stream must be closed after use.
     * @throws Exception if any error occurs.
     */
    @Throws(Exception::class)
    fun getAll(): Stream<Customer>

    /**
     * Get customer as Optional by id.
     *
     * @param id unique identifier of the customer.
     * @return a customer if a customer with unique identifier [id] exists, null otherwise.
     * @throws Exception if any error occurs.
     */
    @Throws(Exception::class)
    fun getById(id: Int): Customer?

    /**
     * Add a customer.
     *
     * @param customer the customer to be added.
     * @return true if customer is successfully added, false if customer already exists.
     * @throws Exception if any error occurs.
     */
    @Throws(Exception::class)
    fun add(customer: Customer): Boolean

    /**
     * Update a customer.
     *
     * @param customer the customer to be updated.
     * @return true if customer exists and is successfully updated, false otherwise.
     * @throws Exception if any error occurs.
     */
    @Throws(Exception::class)
    fun update(customer: Customer): Boolean

    /**
     * Delete a customer.
     *
     * @param customer the customer to be deleted.
     * @return true if customer exists and is successfully deleted, false otherwise.
     * @throws Exception if any error occurs.
     */
    @Throws(Exception::class)
    fun delete(customer: Customer): Boolean
}
