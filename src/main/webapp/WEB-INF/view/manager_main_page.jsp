<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manager</title>
</head>
<body>
    <h1>Менеджер</h1>
    <form method="post" action="/manager_main_page">
        <p><input class="button" type="submit" value="Зарегистрировать пользователя"></p>
    </form>
    <%
        Object str = request.getAttribute("Success");
        if (str != null) {
            out.print(str);
        }
    %>
    <p><a href="/logout">Выйти</a></p>
</body>
</html>
