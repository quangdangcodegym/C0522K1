<%--
  Created by IntelliJ IDEA.
  User: URIGOO
  Date: 09/09/2022
  Time: 3:18 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        String myMessage = "";
        String message = (String) request.getAttribute("message");
        if(message!=null){
            myMessage = message;
        }
    %>

    <h1>Message <%= myMessage%></h1>
    <h1>Login form</h1>
    <form action="/login" method="post">
        <input type="text" name="username" />
        <input type="password" name="password" />
        <button type="submit">Dang nhap</button>
    </form>
</body>
</html>
