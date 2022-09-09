<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<%!  %>
<% String user = (String) request.getAttribute("user"); %>
<h1>TRANG CHU</h1>
<h1>Chao <%= user%> Dep trai</h1>
<h1>Chao theo kieu jstl <c:out value="${requestScope.user}"></c:out></h1>


<%
    List<String> jUsers = (List<String>) request.getAttribute("listUsers");
    for(String item : jUsers){
        out.println("<li>" + item + "</li>");
    }
%>

<h1>DUNG THE JSTL FOR EACH</h1>

<c:forEach items="${requestScope.listUsers}" var="c">
    <c:choose>
        <c:when test='${c=="luong"}'>
            <li>${c} be de</li>
        </c:when>
        <c:otherwise>
            <li>${c}</li>
        </c:otherwise>
    </c:choose>
</c:forEach>
</body>
</html>