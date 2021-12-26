package model;

import utils.DBQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Manager extends User{
    public Manager(String fullName) {
        super(Role.MANAGER, fullName);
    }

    public void removeCustomer(Connection connection, Master master, Customer customer) throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        PreparedStatement removeCustomer = connection.prepareStatement(DBQueries.removeCustomer());
        removeCustomer.setString(1, master.fullName);
        removeCustomer.setString(2, customer.fullName);
        removeCustomer.setString(3, customer.licencePlate);
        removeCustomer.setString(4, formatter.format(customer.date));
        removeCustomer.setInt(5, customer.price);
        removeCustomer.executeUpdate();
    }
}
