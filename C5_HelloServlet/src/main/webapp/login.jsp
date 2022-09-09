<%--
  Created by IntelliJ IDEA.
  User: URIGOO
  Date: 09/09/2022
  Time: 3:18 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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




    <c:if test="${requestScope.message != null}">
        <h1> Error ${requestScope.message}</h1>
    </c:if>
    <h1>Login form</h1>
    <form action="/login" method="post">
        <input type="text" name="username" />
        <input type="password" name="password" />
        <button type="submit">Dang nhap</button>
    </form>
</body>
</html>
