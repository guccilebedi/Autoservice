<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager</title>
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
    <form method="post" action="/manager_main_page">
        <p><input class="button" type="submit" value="Зарегистрировать пользователя"></p>
    </form>
    <p><a href="/logout">Выйти</a></p>
</body>
</html>
