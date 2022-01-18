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

public class AppServletTest extends Mockito{
  private String msgPartOne = "<h1>This Server Doesn't Support";
  private String msgPartTwo = "Requests</h1>\n"
      + "<h2>Use a GET request with boolean values for the following parameters<h2>\n"
      + "<h3>'name'</h3>\n<h3>'bus'</h3>\n<h3>'sports'</h3>\n<h3>'sci'</h3>\n<h3>'world'</h3>";
  private String destination = "newsDisplay.jsp";

  @Test
  public void testDoGet() throws Exception {
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
  public void testDoPost() throws Exception {
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
  public void testDoPut() throws Exception {
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
  public void testDoDelete() throws Exception {
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
