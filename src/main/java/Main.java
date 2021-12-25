import service.*;
import model.*;
import utils.DBConnector;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnector.connect();
        AuthorizationService authorizationService = new AuthorizationService(connection);
        Scanner in = new Scanner(System.in);
        System.out.println("Введите логин пользователя: ");
        String userName = in.nextLine();
        System.out.println("Введите пароль пользователя: ");
        String password = in.nextLine();
        List loginResult = authorizationService.login(userName, password);
        if (loginResult.get(0) != null) {
            System.out.println("\n Добро пожаловать, " + loginResult.get(1) + "!");
            boolean running = true;
            while (running) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                System.out.println("\n Выберите действие: ");
                if (loginResult.get(0) == Role.MANAGER) {
                    Manager manager = new Manager((String) loginResult.get(1));
                    System.out.println("1 - Создать новую учётную запись пользователя");
                    System.out.println("2 - Удалить учётную запись пользователя");
                    System.out.println("3 - Просмотреть список клиентов мастера");
                    System.out.println("4 - Добавить запись о клиенте");
                    System.out.println("5 - Удалить запись о клиенте");
                    System.out.println("6 - Завершить программу \n");
                    switch (Integer.parseInt(in.nextLine())) {
                        case 1 -> {
                            System.out.println("Введите роль нового пользователя: ");
                            Role role = null;
                            String temp = in.nextLine();
                            if (temp.equals("master")) {
                                role = Role.MASTER;
                            } else if (temp.equals("manager")) {
                                role = Role.MANAGER;
                            }
                            if (role == null) {
                                System.err.println("Роль введена неправильно. ");
                                break;
                            }
                            System.out.println("Введите логин нового пользователя: ");
                            String newUserName = in.nextLine();
                            System.out.println("Введите пароль нового пользователя: ");
                            String newUserPassword = in.nextLine();
                            System.out.println("Введите ФИО нового пользователя: ");
                            String newUserFullName = in.nextLine();
                            if (role == Role.MASTER) {
                                authorizationService.addUser(manager, newUserName, newUserPassword, newUserFullName, Role.MASTER);
                            } else {
                                authorizationService.addUser(manager, newUserName, newUserPassword, newUserFullName, Role.MANAGER);
                            }
                        }
                        case 2 -> {
                            System.out.println("Введите ФИО пользователя, учётную запись которого хотите удалить: ");
                            String userFullName = in.nextLine();
                            authorizationService.removeUser(manager, userFullName);
                            System.out.println("Операция выполнена!");
                        }
                        case 3 -> {
                            System.out.println("Введите ФИО мастера для просмотра его клиентов: ");
                            String masterFullName = in.nextLine();
                            List<Customer> customers = manager.getCustomers(connection, masterFullName);
                            if (!customers.isEmpty()) {
                                System.out.println(customers);
                                System.out.println("Операция выполнена!");
                            } else {
                                System.err.println("Операция не выполнена!");
                            }
                        }
                        case 4 -> {
                            System.out.println("Введите ФИО мастера для добавления записи о клиенте: ");
                            String masterFullName = in.nextLine();
                            System.out.println("Введите ФИО клиента: ");
                            String customerFullName = in.nextLine();
                            System.out.println("Введите марку автомобиля клиента: ");
                            String carMake = in.nextLine();
                            System.out.println("Введите модель автомобиля клиента: ");
                            String carModel = in.nextLine();
                            System.out.println("Введите гос. номер автомобиля клиента: ");
                            String licencePlate = in.nextLine();
                            System.out.println("Введите дату посещения: ");
                            Date date;
                            try {
                                date = formatter.parse(in.nextLine());
                            } catch (ParseException e) {
                                System.err.println("Дата введена неверно!");
                                break;
                            }
                            System.out.println("Введите сумму чека: ");
                            int price = Integer.parseInt(in.nextLine());
                            Customer customer = new Customer(customerFullName, carMake, carModel, licencePlate, date, price);
                            manager.addCustomer(connection, new Master(masterFullName), customer);
                            System.out.println("Операция выполнена!");
                        }
                        case 5 -> {
                            System.out.println("Введите ФИО мастера для удаления записи о клиенте: ");
                            String masterFullName = in.nextLine();
                            System.out.println("Введите ФИО клиента: ");
                            String customerFullName = in.nextLine();
                            System.out.println("Введите марку автомобиля клиента: ");
                            String carMake = in.nextLine();
                            System.out.println("Введите модель автомобиля клиента: ");
                            String carModel = in.nextLine();
                            System.out.println("Введите гос. номер автомобиля клиента: ");
                            String licencePlate = in.nextLine();
                            System.out.println("Введите дату посещения: ");
                            Date date;
                            try {
                                date = formatter.parse(in.nextLine());
                            } catch (ParseException e) {
                                System.err.println("Дата введена неверно!");
                                break;
                            }
                            System.out.println("Введите сумму чека: ");
                            int price = Integer.parseInt(in.nextLine());
                            Customer customer = new Customer(customerFullName, carMake, carModel, licencePlate, date, price);
                            manager.removeCustomer(connection, new Master(masterFullName), customer);
                            System.out.println("Операция выполнена!");
                        }
                        case 6 -> running = false;
                    }
                } else if (loginResult.get(0) == Role.MASTER) {
                    Master master = new Master((String) loginResult.get(1));
                    System.out.println("1 - Добавить запись о клиенте");
                    System.out.println("2 - Завершить программу");
                    switch (Integer.parseInt(in.nextLine())) {
                        case 1 -> {
                            System.out.println("Введите ФИО клиента: ");
                            String customerFullName = in.nextLine();
                            System.out.println("Введите марку автомобиля клиента: ");
                            String carMake = in.nextLine();
                            System.out.println("Введите модель автомобиля клиента: ");
                            String carModel = in.nextLine();
                            System.out.println("Введите гос. номер автомобиля клиента: ");
                            String licencePlate = in.nextLine();
                            System.out.println("Введите дату посещения: ");
                            Date date;
                            try {
                                date = formatter.parse(in.nextLine());
                            } catch (ParseException e) {
                                System.err.println("Дата введена неверно!");
                                break;
                            }
                            System.out.println("Введите сумму чека: ");
                            int price = Integer.parseInt(in.nextLine());
                            Customer customer = new Customer(customerFullName, carMake, carModel, licencePlate, date, price);
                            master.addCustomer(connection, master, customer);
                            System.out.println("Операция выполнена!");
                        }
                        case 2 -> running = false;
                    }
                }
            }
        } else {
            System.err.println("Введены неверные данные!");
        }
    }
}