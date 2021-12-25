package utils;

public class DBQueries {
    public static String login() {
        return "select e.role, e.full_name from employees e where id = (select u.id from users u where u.login = ? and u.password = ?)";
    }

    public static String register() {
        return "insert into users (login, password) values (?, ?); insert into employees (full_name, role) values (?, ?)";
    }

    public static String addCustomer() {
        return "insert into customers_visits (masters_id, full_name, car_make, car_model, licence_plate, visit_date, price) values ((select e.id from employees e where e.full_name = ?), ?, ?, ?, ?, to_date(?, 'DD MM YYYY'), ?)";
    }

    public static String removeCustomer() {
        return "delete from customers_visits cv where cv.masters_id = (select e.id from employees e where e.full_name = ?) and licence_plate = ? and visit_date = to_date(?, 'DD MM YYYY')";
    }

    public static String getCustomers() {
        return "select cv.* from customers_visits cv where cv.masters_id = (select e.id from employees e where e.full_name = ?)";
    }

    public static String removeUser() {
        return "delete from users u where u.id = (select e.id from employees e where e.full_name = ?); delete from employees e where e.full_name = ?";
    }
}
