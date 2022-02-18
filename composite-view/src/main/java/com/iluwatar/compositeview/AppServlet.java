package com.iluwatar.compositeview;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A servlet object that extends HttpServlet.
 * Runs on Tomcat 10 and handles Http requests
 */

public final class AppServlet extends HttpServlet {
  private String msgPartOne = "<h1>This Server Doesn't Support";
  private String msgPartTwo = "Requests</h1>\n"
      + "<h2>Use a GET request with boolean values for the following parameters<h2>\n"
      + "<h3>'name'</h3>\n<h3>'bus'</h3>\n<h3>'sports'</h3>\n<h3>'sci'</h3>\n<h3>'world'</h3>";

  private String destination = "newsDisplay.jsp";

  public AppServlet() {

  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    RequestDispatcher requestDispatcher = req.getRequestDispatcher(destination);
    ClientPropertiesBean reqParams = new ClientPropertiesBean(req);
    req.setAttribute("properties", reqParams);
    requestDispatcher.forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    resp.setContentType("text/html");
    try (PrintWriter out = resp.getWriter()) {
      out.println(msgPartOne + " Post " + msgPartTwo);
    }

  }

  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    resp.setContentType("text/html");
    try (PrintWriter out = resp.getWriter()) {
      out.println(msgPartOne + " Delete " + msgPartTwo);
    }
  }

  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    resp.setContentType("text/html");
    try (PrintWriter out = resp.getWriter()) {
      out.println(msgPartOne + " Put " + msgPartTwo);
    }
  }
}
