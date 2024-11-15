/*
 * This project is licensed under the MIT license. Module model-view-viewmodel
 *  is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
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
/**
 * Context and problem
 * Most applications require a way to interact with a database to perform
 * CRUD (Create, Read, Update, Delete) operations.
 * Traditionally, this involves writing a lot of boilerplate code
 * for database access, which can be error-prone and hard to maintain.
 * This is especially true in applications with
 * complex data models and relationships.
 *
 * <p>Often, developers need to write separate classes for database access and
 * domain models, leading to a separation of concerns
 * that can make the codebase more complex and harder to understand.
 * Additionally, changes to the database schema can require significant
 * changes to the data access code, increasing the maintenance burden.
 *
 * <p>Maintaining this separation can force the application to
 * adhere to at least some of the database's APIs or other semantics.
 * When these database features have quality issues, supporting them "corrupts"
 * what might otherwise be a cleanly designed application.
 * Similar issues can arise with any external system that your
 * development team doesn't control, not just databases.
 *
 * <p>Solution Simplify database interactions by using
 * the Active Record pattern.
 * This pattern encapsulates database access logic within the
 * domain model itself, allowing objects to be responsible
 * for their own persistence.
 * The Active Record pattern provides methods for
 * CRUD operations directly within the model class,
 * making database interactions straightforward and intuitive.
 */

package com.iluwatar.activerecord;
