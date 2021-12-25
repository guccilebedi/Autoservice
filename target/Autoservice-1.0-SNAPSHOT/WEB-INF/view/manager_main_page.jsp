<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager</title>
</head>
<body>
<form action="" method="get">
    <h1>Менеджер</h1>
    <%= request.getParameter("msg")%>
    <a href="/logout">Выйти</a>
</form>
</body>
</html>
