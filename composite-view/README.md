---
title: Composite View 
category: Structural
language: en
tag:
- Enterprise patterns
- Presentation
---

## Name
**Composite View**

## Intent
The purpose of the Composite View Pattern is to increase re-usability and flexibility when creating views for websites/webapps. 
This pattern seeks to decouple the content of the page from its layout, allowing changes to be made to either the content
or layout of the page without impacting the other. This pattern also allows content to be easily reused across different views easily.

## Explanation
Real World Example
> A news site wants to display the current date and news to different users
> based on that user's preferences. The news site will substitute in different news feed
> components depending on the user's interest, defaulting to local news.

In Plain Words
> Composite View Pattern is having a main view being composed of smaller subviews.
> The layout of this composite view is based on a template. A View-manager then decides which
> subviews to include in this template.

Wikipedia Says
> Composite views that are composed of multiple atomic subviews. Each component of 
> the template may be included dynamically into the whole and the layout of the page may be managed independently of the content.
> This solution provides for the creation of a composite view based on the inclusion and substitution of 
> modular dynamic and static template fragments. 
> It promotes the reuse of atomic portions of the view by encouraging modular design.

**Programmatic Example**

Since this is a web development pattern, a server is required to demonstrate it.
This example uses Tomcat 10.0.13 to run the servlet, and this programmatic example will only work with Tomcat 10+.

Firstly there is `AppServlet` which is an `HttpServlet` that runs on Tomcat 10+.
```java
public class AppServlet extends HttpServlet {
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
    PrintWriter out = resp.getWriter();
    out.println(msgPartOne + " Post " + msgPartTwo);

  }

  @Override
  public void doDelete(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();
    out.println(msgPartOne + " Delete " + msgPartTwo);

  }

  @Override
  public void doPut(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();
    out.println(msgPartOne + " Put " + msgPartTwo);

  }
}

```
This servlet is not part of the pattern, and simply forwards GET requests to the correct JSP.
PUT, POST, and DELETE requests are not supported and will simply show an error message.

The view management in this example is done via a javabean class: `ClientPropertiesBean`, which stores user preferences.
```java
public class ClientPropertiesBean implements Serializable {

  private static final String WORLD_PARAM = "world";
  private static final String SCIENCE_PARAM = "sci";
  private static final String SPORTS_PARAM = "sport";
  private static final String BUSINESS_PARAM = "bus";
  private static final String NAME_PARAM = "name";

  private static final String DEFAULT_NAME = "DEFAULT_NAME";
  private boolean worldNewsInterest;
  private boolean sportsInterest;
  private boolean businessInterest;
  private boolean scienceNewsInterest;
  private String name;
  
  public ClientPropertiesBean() {
    worldNewsInterest = true;
    sportsInterest = true;
    businessInterest = true;
    scienceNewsInterest = true;
    name = DEFAULT_NAME;

  }
  
  public ClientPropertiesBean(HttpServletRequest req) {
    worldNewsInterest = Boolean.parseBoolean(req.getParameter(WORLD_PARAM));
    sportsInterest = Boolean.parseBoolean(req.getParameter(SPORTS_PARAM));
    businessInterest = Boolean.parseBoolean(req.getParameter(BUSINESS_PARAM));
    scienceNewsInterest = Boolean.parseBoolean(req.getParameter(SCIENCE_PARAM));
    String tempName = req.getParameter(NAME_PARAM);
    if (tempName == null || tempName == "") {
      tempName = DEFAULT_NAME;
    }
    name = tempName;
  }
  // getters and setters generated by Lombok 
}
```
This javabean has a default constructor, and another that takes an `HttpServletRequest`.
This second constructor takes the request object, parses out the request parameters which contain the
user preferences for different types of news.

The template for the news page is in `newsDisplay.jsp`
```html
<html>
<head>
    <style>
        h1 { text-align: center;}
        h2 { text-align: center;}
        h3 { text-align: center;}
        .centerTable {
            margin-left: auto;
            margin-right: auto;
        }
        table {border: 1px solid black;}
        tr {text-align: center;}
        td {text-align: center;}
    </style>
</head>
<body>
    <%ClientPropertiesBean propertiesBean = (ClientPropertiesBean) request.getAttribute("properties");%>
    <h1>Welcome <%= propertiesBean.getName()%></h1>
    <jsp:include page="header.jsp"></jsp:include>
    <table class="centerTable">

        <tr>
            <td></td>
            <% if(propertiesBean.isWorldNewsInterest()) { %>
                <td><%@include file="worldNews.jsp"%></td>
            <% } else { %>
                <td><%@include file="localNews.jsp"%></td>
            <% } %>
            <td></td>
        </tr>
        <tr>
            <% if(propertiesBean.isBusinessInterest()) { %>
                <td><%@include file="businessNews.jsp"%></td>
            <% } else { %>
                <td><%@include file="localNews.jsp"%></td>
            <% } %>
            <td></td>
            <% if(propertiesBean.isSportsInterest()) { %>
                <td><%@include file="sportsNews.jsp"%></td>
            <% } else { %>
                <td><%@include file="localNews.jsp"%></td>
            <% } %>
        </tr>
        <tr>
            <td></td>
            <% if(propertiesBean.isScienceNewsInterest()) { %>
                <td><%@include file="scienceNews.jsp"%></td>
            <% } else { %>
                <td><%@include file="localNews.jsp"%></td>
            <% } %>
            <td></td>
        </tr>
    </table>
</body>
</html>
```
This JSP page is the template. It declares a table with three rows, with one component in the first row,
two components in the second row, and one component in the third row. 

The scriplets in the file are part of the
view management strategy that include different atomic subviews based on the user preferences in the Javabean.

Here are two examples of the mock atomic subviews used in the composite:
`businessNews.jsp`
```html
<html>
    <head>
        <style>
            h2 { text-align: center;}
            table {border: 1px solid black;}
            tr {text-align: center;}
            td {text-align: center;}
        </style>
    </head>
    <body>
        <h2>
            Generic Business News
        </h2>
        <table style="margin-right: auto; margin-left: auto">
            <tr>
                <td>Stock prices up across the world</td>
                <td>New tech companies to invest in</td>
            </tr>
            <tr>
                <td>Industry leaders unveil new project</td>
                <td>Price fluctuations and what they mean</td>
            </tr>
        </table>
    </body>
</html>
```
`localNews.jsp`
```html
<html>
    <body>
        <div style="text-align: center">
            <h3>
                Generic Local News
            </h3>
            <ul style="list-style-type: none">
                <li>
                    Mayoral elections coming up in 2 weeks
                </li>
                <li>
                    New parking meter rates downtown coming tomorrow
                </li>
                <li>
                    Park renovations to finish by the next year
                </li>
                <li>
                    Annual marathon sign ups available online
                </li>
            </ul>
        </div>
    </body>
</html>
```
The results are as such:

1) The user has put their name as `Tammy` in the request parameters and no preferences: 
![alt text](./etc/images/noparam.png)
2) The user has put their name as `Johnny` in the request parameters and has a preference for world, business, and science news:
![alt text](./etc/images/threeparams.png)

The different subviews such as `worldNews.jsp`, `businessNews.jsp`, etc. are included conditionally
based on the request parameters.

**How To Use**

To try this example, make sure you have Tomcat 10+ installed.
Set up your IDE to build a WAR file from the module and deploy that file to the server

IntelliJ:

Under `Run` and `edit configurations` Make sure Tomcat server is one of the run configurations. 
Go to the deployment tab, and make sure there is one artifact being built called `composite-view:war exploded`. 
If not present, add one.

Ensure that the artifact is being built from the content of the `web` directory and the compilation results of the module.
Point the output of the artifact to a convenient place. Run the configuration and view the landing page, 
follow instructions on that page to continue.

## Class diagram

![alt text](./etc/composite_view.png)

The class diagram here displays the Javabean which is the view manager.
The views are JSP's held inside the web directory.

## Applicability

This pattern is applicable to most websites that require content to be displayed dynamically/conditionally.
If there are components that need to be re-used for multiple views, or if the project requires reusing a template, 
or if it needs to include content depending on certain conditions, then this pattern is a good choice.

## Known uses

Most modern websites use composite views in some shape or form, as they have templates for views and small atomic components
that are included in the page dynamically. Most modern Javascript libraries, like React, support this design pattern 
with components.

## Consequences
**Pros**
* Easy to re-use components
* Change layout/content without affecting the other
* Reduce code duplication
* Code is more maintainable and modular

**Cons**
* Overhead cost at runtime
* Slower response compared to directly embedding elements
* Increases potential for display errors 

## Related patterns
* [Composite (GoF)](https://java-design-patterns.com/patterns/composite/)
* [View Helper](https://www.oracle.com/java/technologies/viewhelper.html)

## Credits
* [Core J2EE Patterns - Composite View](https://www.oracle.com/java/technologies/composite-view.html)
* [Composite View Design Pattern – Core J2EE Patterns](https://www.dineshonjava.com/composite-view-design-pattern/)

