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
<html>
<head>
    <title>Composite Patterns Mock News Site</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h1, h2, h3 {
            text-align: center;
            color: #333;
        }
        h1 {
            font-size: 2.5em;
        }
        h2 {
            font-size: 2em;
            margin-top: 20px;
        }
        h3 {
            font-size: 1.5em;
            margin-top: 10px;
        }
        .parameters {
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <h1>Welcome To The Composite Patterns Mock News Site</h1>
    <div class="parameters">
        <h2>Send a GET request to the "/news" path to see the composite view with mock news</h2>
        <h2>Use the following parameters:</h2>
        <h3>name: <em>string</em> - Your name to be dynamically displayed</h3>
        <h3>bus: <em>boolean</em> - Set to true to see mock business news</h3>
        <h3>world: <em>boolean</em> - Set to true to see mock world news</h3>
        <h3>sci: <em>boolean</em> - Set to true to see mock science news</h3>
        <h3>sport: <em>boolean</em> - Set to true to see mock sports news</h3>
        <h2>Example Request:</h2>
        <h3>/news?name=John&bus=true&world=false&sci=true&sport=false</h3>
        <h3>If the request fails, ensure you have the correct parameters and try again.</h3>
    </div>
</body>
</html>
