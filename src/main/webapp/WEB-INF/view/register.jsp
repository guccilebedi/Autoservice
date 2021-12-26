<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Регистрация нового пользователя</h1><br>
    <form method="post" action="/register">
        <input type="text" placeholder="ФИО" name="newUserFullName"><br>
        <input type="text" placeholder="Логин" name="newUserLogin"><br>
        <input type="password" placeholder="Пароль" name="newUserPassword"><br><br>
        <select name="newUserRole">
            <option value="">Выберите роль</option>
            <option value="master">Мастер</option>
            <option value="manager">Менеджер</option>
        </select><p></p>
        <%
            Object str = request.getAttribute("Success");
            if (str != null) {
                out.print(str);
            }
        %>
        <p><input class="button" type="submit" name="Back" value="Назад"> <input class="button" type="submit" name="Register" value="Зарегистрировать"></p>
    </form>
</div>
</body>
</html>
