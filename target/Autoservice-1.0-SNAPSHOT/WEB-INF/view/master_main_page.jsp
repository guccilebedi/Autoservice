<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Master</title>
</head>
<body>
    <h1>Добро пожаловать,
    <%
        session = request.getSession();
        Object str = session.getAttribute("fullName");
        if (str != null) {
            out.print(str);
        }
    %>!</h1>
    <a href="/logout">Выйти</a>
</body>
</html>
