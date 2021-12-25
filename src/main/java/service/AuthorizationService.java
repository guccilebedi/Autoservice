package service;

import model.*;
import utils.DBQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AuthorizationService {
    private final Connection connection;

    public AuthorizationService(Connection connection){
        this.connection = connection;
    }

    public List login(String userName, String password) throws SQLException {
        List result = new ArrayList();
        PreparedStatement loginStatement = connection.prepareStatement(DBQueries.login());
        loginStatement.setString(1, userName);
        loginStatement.setString(2, password);
        ResultSet resultSet = loginStatement.executeQuery();
        if (resultSet.next()) {
            String role = resultSet.getString("role");
            String fullName = resultSet.getString("full_name");
            if (role.equals("manager")) {
                result.add(Role.MANAGER);
            } else {
                result.add(Role.MASTER);
            }
            result.add(fullName);
        } else {
            result.add(null);
            result.add(null);
        }
        return result;
    }

    public void addUser(User user, String newUserName, String newUserPassword, String newUserFullName, Role role) throws SQLException {
        if (user.role == Role.MANAGER) {
            PreparedStatement register = connection.prepareStatement(DBQueries.register());
            register.setString(1, newUserName);
            register.setString(2, newUserPassword);
            register.setString(3, newUserFullName);
            if (role.equals(Role.MANAGER)) {
                register.setString(4, "manager");
            } else {
                register.setString(4, "master");
            }
            register.executeUpdate();
        }
    }

    public void removeUser(User user, String userFullName) throws SQLException {
        if (user.role == Role.MANAGER) {
            PreparedStatement removeUser = connection.prepareStatement(DBQueries.removeUser());
            removeUser.setString(1, userFullName);
            removeUser.setString(2, userFullName);
            removeUser.executeUpdate();
        }
    }
}
