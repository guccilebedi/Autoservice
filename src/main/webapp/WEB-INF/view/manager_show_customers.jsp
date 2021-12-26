<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete account</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Демонстрация списка клиентов мастера</h1><br>
    <form method="post" action="/manager_show_customers">
        <input type="text" placeholder="ФИО мастера" name="masterFullName"><br>
        <%
            if (request.getAttribute("List") != null) {
                List customers = (List) request.getAttribute("List");
                if (!customers.isEmpty()) {
                    for (int i = 0; i < customers.size(); i++) {
                        Customer customer = (Customer) customers.get(i);
                        out.println(customer.toString() + "<br>");
                    }
                } else {
                    out.print("Записи о визитах клиентов не найдены!");
                }
            }
        %>
        <p><input class="button" type="submit" name="Back" value="Назад"> <input class="button" type="submit" name="Show" value="Показать"></p>
    </form>
</div>
</body>
</html>
