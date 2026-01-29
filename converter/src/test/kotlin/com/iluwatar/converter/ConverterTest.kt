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

// ABOUTME: Tests for the Converter pattern verifying bidirectional conversion correctness.
// ABOUTME: Covers domain-to-DTO, DTO-to-domain, custom converters, and collection conversions.
package com.iluwatar.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Random

/** Tests for [Converter] */
class ConverterTest {

    private val userConverter = UserConverter()

    /** Tests whether a converter created of opposite functions holds equality as a bijection. */
    @Test
    fun testConversionsStartingFromDomain() {
        val u1 = User("Tom", "Hanks", true, "tom@hanks.com")
        val u2 = userConverter.convertFromDto(userConverter.convertFromEntity(u1))
        assertEquals(u1, u2)
    }

    /** Tests whether a converter created of opposite functions holds equality as a bijection. */
    @Test
    fun testConversionsStartingFromDto() {
        val u1 = UserDto("Tom", "Hanks", true, "tom@hanks.com")
        val u2 = userConverter.convertFromEntity(userConverter.convertFromDto(u1))
        assertEquals(u1, u2)
    }

    /**
     * Tests the custom users converter. Thanks to Kotlin lambdas, converter can be easily and
     * cleanly instantiated allowing various different conversion strategies to be implemented.
     */
    @Test
    fun testCustomConverter() {
        val converter = Converter<UserDto, User>(
            fromDto = { userDto ->
                User(
                    firstName = userDto.firstName,
                    lastName = userDto.lastName,
                    isActive = userDto.isActive,
                    userId = Random().nextInt().toString(),
                )
            },
            fromEntity = { user ->
                UserDto(
                    firstName = user.firstName,
                    lastName = user.lastName,
                    isActive = user.isActive,
                    email = user.firstName.lowercase() + user.lastName.lowercase() + "@whatever.com",
                )
            },
        )
        val u1 = User("John", "Doe", false, "12324")
        val userDto = converter.convertFromEntity(u1)
        assertEquals("johndoe@whatever.com", userDto.email)
    }

    /**
     * Test whether converting a collection of Users to DTO Users and then converting them back to
     * domain users returns an equal collection.
     */
    @Test
    fun testCollectionConversion() {
        val users = listOf(
            User("Camile", "Tough", false, "124sad"),
            User("Marti", "Luther", true, "42309fd"),
            User("Kate", "Smith", true, "if0243"),
        )
        val fromDtos = userConverter.createFromDtos(userConverter.createFromEntities(users))
        assertEquals(users, fromDtos)
    }
}
