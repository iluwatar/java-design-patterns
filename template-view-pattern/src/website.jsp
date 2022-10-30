<html>
<head>
  <title>Echoing HTML Request Parameters</title>
</head>
<body>
  <h3>Check the issue allocation for each team member:</h3>
  <form method="get">
    <input type="checkbox" name="members" value="No such person in the Dataset">John Doe
    <input type="checkbox" name="members" value="Template View Pattern">Mary Jane
    <input type="checkbox" name="members" value="Filter Pattern">Robert Smith
    <input type="submit" value="Query">
  </form>

  <%
  String[] authors = request.getParameterValues("members");
  if (authors != null) {
  %>
    <h3>Here is the answer:</h3>
    <ul>
  <%
      for (int i = 0; i < authors.length; ++i) {
  %>
        <li><%= authors[i] %></li>
  <%
      }
  %>

  <h4><% Helper.getName() %></h4>
  <p><% Helper.getDescription() %></p>

    </ul>
    <a href="<%= request.getRequestURI() %>">BACK</a>
  <%
  }
  %>
</body>
</html>