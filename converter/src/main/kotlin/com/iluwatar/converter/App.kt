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

// ABOUTME: Entry point for the Converter pattern demonstration.
// ABOUTME: Shows bidirectional conversion between User domain objects and UserDto transfer objects.
package com.iluwatar.converter

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Converter pattern is a behavioral design pattern which allows a common way of bidirectional
 * conversion between corresponding types (e.g. DTO and domain representations of the logically
 * isomorphic types). Moreover, the pattern introduces a common way of converting a collection of
 * objects between types.
 */
fun main() {
    val userConverter: Converter<UserDto, User> = UserConverter()

    val dtoUser = UserDto("John", "Doe", true, "whatever[at]wherever.com")
    val user = userConverter.convertFromDto(dtoUser)
    logger.info { "Entity converted from DTO: $user" }

    val users = listOf(
        User("Camile", "Tough", false, "124sad"),
        User("Marti", "Luther", true, "42309fd"),
        User("Kate", "Smith", true, "if0243"),
    )
    logger.info { "Domain entities:" }
    users.forEach { logger.info { it.toString() } }

    logger.info { "DTO entities converted from domain:" }
    val dtoEntities = userConverter.createFromEntities(users)
    dtoEntities.forEach { logger.info { it.toString() } }
}
