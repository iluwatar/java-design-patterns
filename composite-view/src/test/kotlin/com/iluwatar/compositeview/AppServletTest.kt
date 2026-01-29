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
// ABOUTME: Unit tests for AppServlet class.
// ABOUTME: Tests HTTP request handling for GET, POST, PUT, and DELETE methods.
package com.iluwatar.compositeview

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.PrintWriter
import java.io.StringWriter

class AppServletTest {

    private val msgPartOne = "<h1>This Server Doesn't Support"
    private val msgPartTwo = """
        Requests</h1>
        <h2>Use a GET request with boolean values for the following parameters<h2>
        <h3>'name'</h3>
        <h3>'bus'</h3>
        <h3>'sports'</h3>
        <h3>'sci'</h3>
        <h3>'world'</h3>
    """.trimIndent()
    private val destination = "newsDisplay.jsp"

    @Test
    fun testDoGet() {
        val mockReq = mockk<HttpServletRequest>()
        val mockResp = mockk<HttpServletResponse>()
        val mockDispatcher = mockk<RequestDispatcher>()
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)

        every { mockResp.writer } returns printWriter
        every { mockReq.getRequestDispatcher(destination) } returns mockDispatcher
        every { mockReq.getParameter(any()) } returns null
        justRun { mockReq.setAttribute(any(), any()) }
        justRun { mockDispatcher.forward(mockReq, mockResp) }

        val curServlet = AppServlet()
        curServlet.doGet(mockReq, mockResp)

        verify(exactly = 1) { mockReq.getRequestDispatcher(destination) }
        verify { mockDispatcher.forward(mockReq, mockResp) }
    }

    @Test
    fun testDoPost() {
        val mockReq = mockk<HttpServletRequest>()
        val mockResp = mockk<HttpServletResponse>()
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)

        every { mockResp.writer } returns printWriter
        justRun { mockResp.contentType = any() }

        val curServlet = AppServlet()
        curServlet.doPost(mockReq, mockResp)
        printWriter.flush()

        assertTrue(stringWriter.toString().contains("$msgPartOne Post $msgPartTwo"))
    }

    @Test
    fun testDoPut() {
        val mockReq = mockk<HttpServletRequest>()
        val mockResp = mockk<HttpServletResponse>()
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)

        every { mockResp.writer } returns printWriter
        justRun { mockResp.contentType = any() }

        val curServlet = AppServlet()
        curServlet.doPut(mockReq, mockResp)
        printWriter.flush()

        assertTrue(stringWriter.toString().contains("$msgPartOne Put $msgPartTwo"))
    }

    @Test
    fun testDoDelete() {
        val mockReq = mockk<HttpServletRequest>()
        val mockResp = mockk<HttpServletResponse>()
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)

        every { mockResp.writer } returns printWriter
        justRun { mockResp.contentType = any() }

        val curServlet = AppServlet()
        curServlet.doDelete(mockReq, mockResp)
        printWriter.flush()

        assertTrue(stringWriter.toString().contains("$msgPartOne Delete $msgPartTwo"))
    }
}
