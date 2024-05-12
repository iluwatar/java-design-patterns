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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

/* Written with reference from https://stackoverflow.com/questions/5434419/how-to-test-my-servlet-using-junit
and https://stackoverflow.com/questions/50211433/servlets-unit-testing
 */

class AppServletTest extends Mockito{
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

  @Test
  void testDoGet() throws Exception {
    HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
    RequestDispatcher mockDispatcher = Mockito.mock(RequestDispatcher.class);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    when(mockResp.getWriter()).thenReturn(printWriter);
    when(mockReq.getRequestDispatcher(destination)).thenReturn(mockDispatcher);
    AppServlet curServlet = new AppServlet();
    curServlet.doGet(mockReq, mockResp);
    verify(mockReq, times(1)).getRequestDispatcher(destination);
    verify(mockDispatcher).forward(mockReq, mockResp);


  }

  @Test
  void testDoPost() throws Exception {
    HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    when(mockResp.getWriter()).thenReturn(printWriter);

    AppServlet curServlet = new AppServlet();
    curServlet.doPost(mockReq, mockResp);
    printWriter.flush();
    assertTrue(stringWriter.toString().contains(msgPartOne + " Post " + msgPartTwo));
  }

  @Test
  void testDoPut() throws Exception {
    HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    when(mockResp.getWriter()).thenReturn(printWriter);

    AppServlet curServlet = new AppServlet();
    curServlet.doPut(mockReq, mockResp);
    printWriter.flush();
    assertTrue(stringWriter.toString().contains(msgPartOne + " Put " + msgPartTwo));
  }

  @Test
  void testDoDelete() throws Exception {
    HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse mockResp = Mockito.mock(HttpServletResponse.class);
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    when(mockResp.getWriter()).thenReturn(printWriter);

    AppServlet curServlet = new AppServlet();
    curServlet.doDelete(mockReq, mockResp);
    printWriter.flush();
    assertTrue(stringWriter.toString().contains(msgPartOne + " Delete " + msgPartTwo));
  }
}
