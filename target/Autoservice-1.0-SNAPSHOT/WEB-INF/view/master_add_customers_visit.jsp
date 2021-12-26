<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Customers Visit</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Добавление записи о визите клиента</h1><br>
    <form method="post" action="/master_add_customers_visit">
        <input type="text" placeholder="ФИО клиента" name="customersFullName"><br>
        <input type="text" placeholder="Марка автомобиля" name="customersCarMake"><br>
        <input type="text" placeholder="Модель автомобиля" name="customersCarModel"><br>
        <input type="text" placeholder="Гос. номер" name="customersLicencePlate"><br>
        <input type="text" placeholder="Дата посещения" name="customersDate"><br>
        <input type="text" placeholder="Итоговая стоимость" name="customersPrice"><br>
        <%
            Object str = request.getAttribute("Success");
            if (str != null) {
                out.print(str);
            }
        %>
        <p><input class="button" type="submit" name="Back" value="Назад"> <input class="button" type="submit" name="Add" value="Добавить"></p>
    </form>
</div>
</body>
</html>
