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
// ABOUTME: Unit tests for ClientPropertiesBean class.
// ABOUTME: Tests default constructor, property accessors, and request parsing constructor.
package com.iluwatar.compositeview

import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ClientPropertiesBeanTest {

    @Test
    fun testDefaultConstructor() {
        val newBean = ClientPropertiesBean()
        assertEquals("DEFAULT_NAME", newBean.name)
        assertTrue(newBean.businessInterest)
        assertTrue(newBean.scienceNewsInterest)
        assertTrue(newBean.sportsInterest)
        assertTrue(newBean.worldNewsInterest)
    }

    @Test
    fun testNameGetterSetter() {
        val newBean = ClientPropertiesBean()
        assertEquals("DEFAULT_NAME", newBean.name)

        newBean.name = "TEST_NAME_ONE"
        assertEquals("TEST_NAME_ONE", newBean.name)
    }

    @Test
    fun testBusinessSetterGetter() {
        val newBean = ClientPropertiesBean()
        assertTrue(newBean.businessInterest)

        newBean.businessInterest = false
        assertFalse(newBean.businessInterest)
    }

    @Test
    fun testScienceSetterGetter() {
        val newBean = ClientPropertiesBean()
        assertTrue(newBean.scienceNewsInterest)

        newBean.scienceNewsInterest = false
        assertFalse(newBean.scienceNewsInterest)
    }

    @Test
    fun testSportsSetterGetter() {
        val newBean = ClientPropertiesBean()
        assertTrue(newBean.sportsInterest)

        newBean.sportsInterest = false
        assertFalse(newBean.sportsInterest)
    }

    @Test
    fun testWorldSetterGetter() {
        val newBean = ClientPropertiesBean()
        assertTrue(newBean.worldNewsInterest)

        newBean.worldNewsInterest = false
        assertFalse(newBean.worldNewsInterest)
    }

    @Test
    fun testRequestConstructor() {
        val mockReq = mockk<HttpServletRequest>()
        every { mockReq.getParameter("world") } returns null
        every { mockReq.getParameter("sport") } returns null
        every { mockReq.getParameter("bus") } returns null
        every { mockReq.getParameter("sci") } returns null
        every { mockReq.getParameter("name") } returns null

        val newBean = ClientPropertiesBean(mockReq)

        assertEquals("DEFAULT_NAME", newBean.name)
        assertFalse(newBean.worldNewsInterest)
        assertFalse(newBean.businessInterest)
        assertFalse(newBean.scienceNewsInterest)
        assertFalse(newBean.sportsInterest)
    }
}
