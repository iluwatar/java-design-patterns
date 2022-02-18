<%--
  Created by IntelliJ IDEA.
  User: Kevin
  Date: 11/29/2021
  Time: 1:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date"%>
<html>
<head>
    <style>
        h1 { text-align: center;}
        h2 { text-align: center;}
        h3 { text-align: center;}
    </style>
</head>
<body>
    <% String todayDateStr = (new Date().toString()); %>
    <h1>Today's Personalized Frontpage</h1>
    <h2><%=todayDateStr%></h2>
</body>
</html>
