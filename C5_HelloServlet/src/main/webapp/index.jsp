<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
</body>
</html>