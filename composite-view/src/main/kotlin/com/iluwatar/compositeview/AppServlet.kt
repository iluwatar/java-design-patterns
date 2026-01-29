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
// ABOUTME: Servlet that handles HTTP requests for the composite view pattern.
// ABOUTME: Runs on Tomcat 10 and demonstrates GET/POST/PUT/DELETE request handling.
package com.iluwatar.compositeview

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

private val logger = KotlinLogging.logger {}

/** A servlet object that extends HttpServlet. Runs on Tomcat 10 and handles Http requests */
class AppServlet : HttpServlet() {

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

    internal var destination = "newsDisplay.jsp"

    public override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        try {
            val requestDispatcher = req.getRequestDispatcher(destination)
            val reqParams = ClientPropertiesBean(req)
            req.setAttribute("properties", reqParams)
            requestDispatcher.forward(req, resp)
        } catch (e: Exception) {
            logger.error(e) { "Exception occurred GET request processing " }
        }
    }

    public override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.contentType = CONTENT_TYPE
        try {
            resp.writer.use { out ->
                out.println("$msgPartOne Post $msgPartTwo")
            }
        } catch (e: Exception) {
            logger.error(e) { "Exception occurred POST request processing " }
        }
    }

    public override fun doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.contentType = CONTENT_TYPE
        try {
            resp.writer.use { out ->
                out.println("$msgPartOne Delete $msgPartTwo")
            }
        } catch (e: Exception) {
            logger.error(e) { "Exception occurred DELETE request processing " }
        }
    }

    public override fun doPut(req: HttpServletRequest, resp: HttpServletResponse) {
        resp.contentType = CONTENT_TYPE
        try {
            resp.writer.use { out ->
                out.println("$msgPartOne Put $msgPartTwo")
            }
        } catch (e: Exception) {
            logger.error(e) { "Exception occurred PUT request processing " }
        }
    }

    companion object {
        private const val CONTENT_TYPE = "text/html"
    }
}
