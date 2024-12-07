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
package com.iluwatar.viewhelper;
// Add package declaration here


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Initializing the User object before each test
        user = new User("John Doe", 30, "john.doe@example.com");
    }

    @Test
    void testGetName() {
        assertEquals("John Doe", user.getName(), "User's name should be 'John Doe'");
    }

    @Test
    void testGetAge() {
        assertEquals(30, user.getAge(), "User's age should be 30");
    }

    @Test
    void testGetEmail() {
        assertEquals("john.doe@example.com", user.getEmail(), "User's email should be 'john.doe@example.com'");
    }

    @Test
    void testConstructorWithValidValues() {
        User newUser = new User("Jane Doe", 25, "jane.doe@example.com");
        assertNotNull(newUser);
        assertEquals("Jane Doe", newUser.getName());
        assertEquals(25, newUser.getAge());
        assertEquals("jane.doe@example.com", newUser.getEmail());
    }

    @Test
    void testConstructorWithInvalidEmail() {
        // Test with invalid email (edge case)
        User invalidUser = new User("Invalid User", 40, "invalidemail");
        assertEquals("invalidemail", invalidUser.getEmail(), "Email should be 'invalidemail'");
    }

    @Test
    void testGetNameNotNull() {
        assertNotNull(user.getName(), "User's name should not be null");
    }

    @Test
    void testAgePositive() {
        assertTrue(user.getAge() > 0, "User's age should be a positive number");
    }
}
