package model;

import utils.DBQueries;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class User {
    public final Role role;
    public final String fullName;

    public User(Role role, String fullName) {
        this.role = role;
        this.fullName = fullName;
    }

    public void addCustomer(Connection connection, Master master, Customer customer) throws SQLException {
        PreparedStatement addCustomer = connection.prepareStatement(DBQueries.addCustomer());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        addCustomer.setString(1, master.fullName);
        addCustomer.setString(2, customer.fullName);
        addCustomer.setString(3, customer.carMake);
        addCustomer.setString(4, customer.carModel);
        addCustomer.setString(5, customer.licencePlate);
        addCustomer.setString(6, formatter.format(customer.date));
        addCustomer.setInt(7, customer.price);
        addCustomer.executeUpdate();
    }

    public List<Customer> getCustomers(Connection connection, String mastersFullName) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        PreparedStatement getCustomers = connection.prepareStatement(DBQueries.getCustomers());
        getCustomers.setString(1, mastersFullName);
        try {
            ResultSet resultSet = getCustomers.executeQuery();
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getString("full_name"), resultSet.getString("car_make"), resultSet.getString("car_model"), resultSet.getString("licence_plate"), resultSet.getDate("visit_date"), resultSet.getInt("price")));
            }
        } catch (Exception e) {
            return customers;
        }
        return customers;
    }
}
