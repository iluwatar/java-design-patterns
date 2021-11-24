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
    <table class="centerTable">
        <tr>
            <td></td>
            <td>Ad Number 1</td>
            <td></td>
        </tr>
        <tr>
            <td>Ad Number 2</td>
            <td>Ad Number 3</td>
            <td>Ad Number 4</td>
        </tr>
    </table>
</body>
</html>
