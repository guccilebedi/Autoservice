<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete account</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Удаление учётной записи</h1><br>
    <form method="post" action="/delete_account">
        <input type="text" placeholder="ФИО" name="userFullName"><br>
        <%
            Object str = request.getAttribute("Success");
            if (str != null) {
                out.print(str);
            }
        %>
        <p><input class="button" type="submit" name="Back" value="Назад"> <input class="button" type="submit" name="Delete" value="Удалить"></p>
    </form>
</div>
</body>
</html>
