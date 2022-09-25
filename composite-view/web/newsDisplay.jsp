<%--

    This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).

    The MIT License
    Copyright © 2014-2022 Ilkka Seppälä

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.iluwatar.compositeview.ClientPropertiesBean"%>
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
