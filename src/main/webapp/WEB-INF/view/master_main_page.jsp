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
    <form method="post" action="/master_main_page">
        <p><input class="button" type="submit" name="Add" value="Добавить запись о визите клиента"></p>
        <p><input class="button" type="submit" name="Show" value="Показать список записей о визитах клиентов"></p>
    </form>
    <a href="/logout">Выйти</a>
</body>
</html>
