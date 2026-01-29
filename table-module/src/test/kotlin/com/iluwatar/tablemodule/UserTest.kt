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
package com.iluwatar.tablemodule

// ABOUTME: Tests for the User data class covering equality, hashCode, copy, and property access.
// ABOUTME: Validates Kotlin data class behavior equivalent to the original Lombok-annotated Java class.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class UserTest {

    @Test
    fun testEquals1() {
        val user = User(1, "janedoe", "iloveyou")
        assertNotEquals(user, User(123, "abcd", "qwerty"))
    }

    @Test
    fun testEquals2() {
        val user = User(1, "janedoe", "iloveyou")
        assertEquals(user, User(1, "janedoe", "iloveyou"))
    }

    @Test
    fun testEquals3() {
        val user = User(123, "janedoe", "iloveyou")
        assertNotEquals(user, User(1, "janedoe", "iloveyou"))
    }

    @Test
    fun testEquals4() {
        val user = User(1, null, "iloveyou")
        assertNotEquals(user, User(1, "janedoe", "iloveyou"))
    }

    @Test
    fun testEquals5() {
        val user = User(1, "iloveyou", "iloveyou")
        assertNotEquals(user, User(1, "janedoe", "iloveyou"))
    }

    @Test
    fun testEquals6() {
        val user = User(1, "janedoe", "janedoe")
        assertNotEquals(user, User(1, "janedoe", "iloveyou"))
    }

    @Test
    fun testEquals7() {
        val user = User(1, "janedoe", null)
        assertNotEquals(user, User(1, "janedoe", "iloveyou"))
    }

    @Test
    fun testEquals8() {
        val user = User(1, null, "iloveyou")
        assertEquals(user, User(1, null, "iloveyou"))
    }

    @Test
    fun testEquals9() {
        val user = User(1, "janedoe", null)
        assertEquals(user, User(1, "janedoe", null))
    }

    @Test
    fun testHashCodeConsistency() {
        val user1 = User(1, "janedoe", "iloveyou")
        val user2 = User(1, "janedoe", "iloveyou")
        assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun testHashCodeWithNullUsername() {
        val user1 = User(1, null, "iloveyou")
        val user2 = User(1, null, "iloveyou")
        assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun testHashCodeWithNullPassword() {
        val user1 = User(1, "janedoe", null)
        val user2 = User(1, "janedoe", null)
        assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun testSetId() {
        val user = User(1, "janedoe", "iloveyou")
        user.id = 2
        assertEquals(2, user.id)
    }

    @Test
    fun testSetPassword() {
        val user = User(1, "janedoe", "tmp")
        user.password = "iloveyou"
        assertEquals("iloveyou", user.password)
    }

    @Test
    fun testSetUsername() {
        val user = User(1, "tmp", "iloveyou")
        user.username = "janedoe"
        assertEquals("janedoe", user.username)
    }

    @Test
    fun testToString() {
        val user = User(1, "janedoe", "iloveyou")
        assertEquals(
            "User(id=${user.id}, username=${user.username}, password=${user.password})",
            user.toString()
        )
    }
}
