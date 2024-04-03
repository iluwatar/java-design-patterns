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
package com.iluwatar.compositeview;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * A servlet object that extends HttpServlet.
 * Runs on Tomcat 10 and handles Http requests
 */
@Slf4j
public final class AppServlet extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  private String msgPartOne = "<h1>This Server Doesn't Support";
  private String msgPartTwo = """
          Requests</h1>
          <h2>Use a GET request with boolean values for the following parameters<h2>
          <h3>'name'</h3>
          <h3>'bus'</h3>
          <h3>'sports'</h3>
          <h3>'sci'</h3>
          <h3>'world'</h3>""";

  private String destination = "newsDisplay.jsp";

  public AppServlet() {

  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      RequestDispatcher requestDispatcher = req.getRequestDispatcher(destination);
      ClientPropertiesBean reqParams = new ClientPropertiesBean(req);
      req.setAttribute("properties", reqParams);
      requestDispatcher.forward(req, resp);
    } catch (Exception e) {
      LOGGER.error("Exception occurred GET request processing ", e);
    }
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) {
    resp.setContentType(CONTENT_TYPE);
    try (PrintWriter out = resp.getWriter()) {
      out.println(msgPartOne + " Post " + msgPartTwo);
    } catch (Exception e) {
      LOGGER.error("Exception occurred POST request processing ", e);
    }
  }

  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    resp.setContentType(CONTENT_TYPE);
    try (PrintWriter out = resp.getWriter()) {
      out.println(msgPartOne + " Delete " + msgPartTwo);
    } catch (Exception e) {
      LOGGER.error("Exception occurred DELETE request processing ", e);
    }
  }

  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp) {
    resp.setContentType(CONTENT_TYPE);
    try (PrintWriter out = resp.getWriter()) {
      out.println(msgPartOne + " Put " + msgPartTwo);
    } catch (Exception e) {
      LOGGER.error("Exception occurred PUT request processing ", e);
    }
  }
}
