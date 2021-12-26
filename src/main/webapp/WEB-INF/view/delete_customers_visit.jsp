<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Customers Visit</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Удаление записи о визите клиента к мастеру</h1><br>
    <form method="post" action="/delete_customers_visit">
        <input type="text" placeholder="ФИО мастера" name="customersMasterFullName"><br>
        <input type="text" placeholder="ФИО клиента" name="customersFullName"><br>
        <input type="text" placeholder="Гос. номер" name="customersLicencePlate"><br>
        <input type="text" placeholder="Дата посещения" name="customersDate"><br>
        <input type="text" placeholder="Итоговая стоимость" name="customersPrice"><br>
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
