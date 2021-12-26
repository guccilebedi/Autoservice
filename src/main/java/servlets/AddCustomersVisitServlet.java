package servlets;

import model.Customer;
import model.Master;
import model.Role;
import utils.DBConnector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.nonNull;

public class AddCustomersVisitServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // метод get
            throws ServletException, IOException {
        final HttpSession session = request.getSession();
        if (!nonNull(session)) { // проверка того, что пользователь авторизован
            if (session.getAttribute("role") == Role.MANAGER) { // если менеджер - перенаправить на его главную страницу
                request.getRequestDispatcher("/WEB-INF/view/manager_add_customers_visit.jsp").forward(request, response);
            } else if (session.getAttribute("role") == Role.MASTER) { // если мастер - перенаправить на его главную страницу
                request.getRequestDispatcher("/WEB-INF/view/master_add_customers_visit.jsp").forward(request, response);
            }
        } else { // иначе перенаправить на страницу входа
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  // метод post
        final HttpSession session = request.getSession();
        if (nonNull(session)) { // проверка того, что пользователь авторизован
            if (session.getAttribute("role") == Role.MANAGER) { // если менеджер - перенаправить его на страницу для менеджера
                if (request.getParameter("Add") != null) { // если нажата кнопка добавить
                    final String customersMasterFullName = request.getParameter("customersMasterFullName");
                    final String customersFullName = request.getParameter("customersFullName");
                    final String customersCarMake = request.getParameter("customersCarMake");
                    final String customersCarModel = request.getParameter("customersCarModel");
                    final String customersLicencePlate = request.getParameter("customersLicencePlate");
                    final String customersDate = request.getParameter("customersDate");
                    final String customersPrice = request.getParameter("customersPrice");
                    if (!customersMasterFullName.equals("") && !customersFullName.equals("") && !customersCarMake.equals("") && !customersCarModel.equals("") && !customersLicencePlate.equals("") && !customersDate.equals("") && !customersPrice.equals("")) { // проверка на заполнение всех полей
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                            Class.forName("org.postgresql.Driver");
                            Connection connection = DBConnector.connect();
                            Master master = new Master(customersMasterFullName);
                            Date temp;
                            try {
                                temp = formatter.parse(customersDate);
                                Customer customer = new Customer(customersFullName, customersCarMake, customersCarModel, customersLicencePlate, temp, Integer.parseInt(customersPrice));
                                master.addCustomer(connection, master, customer);
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/manager_add_customers_visit.jsp");
                                request.setAttribute("Success", "Запись о визите клиента " + customersFullName + " к мастеру " + customersMasterFullName + " успешно добавлена!");
                                dispatcher.forward(request, response);
                            } catch (ParseException e) {
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/manager_add_customers_visit.jsp");
                                request.setAttribute("Success", "Дату необходимо указать в формате дд.мм.гггг!");
                                dispatcher.forward(request, response);
                            }
                        } catch (SQLException | ClassNotFoundException | ServletException | IOException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/manager_add_customers_visit.jsp");
                        request.setAttribute("Success", "Необходимо заполнить все поля!");
                        dispatcher.forward(request, response);
                    }
                } else if (request.getParameter("Back") != null) { // если нажата кнопка назад
                    request.getRequestDispatcher("/WEB-INF/view/manager_main_page.jsp").forward(request, response);
                }
            } else if (session.getAttribute("role") == Role.MASTER) { // если мастер - перенаправить его на страницу для мастера
                if (request.getParameter("Add") != null) { // если нажата кнопка добавить
                final String customersFullName = request.getParameter("customersFullName");
                final String customersCarMake = request.getParameter("customersCarMake");
                final String customersCarModel = request.getParameter("customersCarModel");
                final String customersLicencePlate = request.getParameter("customersLicencePlate");
                final String customersDate = request.getParameter("customersDate");
                final String customersPrice = request.getParameter("customersPrice");
                    if (!customersFullName.equals("") && !customersCarMake.equals("") && !customersCarModel.equals("") && !customersLicencePlate.equals("") && !customersDate.equals("") && !customersPrice.equals("")) { // проверка на заполнение всех полей
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                            Class.forName("org.postgresql.Driver");
                            Connection connection = DBConnector.connect();
                            Master master = new Master((String) session.getAttribute("fullName"));
                            Date temp;
                            try {
                                temp = formatter.parse(customersDate);
                                Customer customer = new Customer(customersFullName, customersCarMake, customersCarModel, customersLicencePlate, temp, Integer.parseInt(customersPrice));
                                master.addCustomer(connection, master, customer);
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/master_add_customers_visit.jsp");
                                request.setAttribute("Success", "Запись о визите клиента " + customersFullName + " успешно добавлена!");
                                dispatcher.forward(request, response);
                            } catch (ParseException e) {
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/master_add_customers_visit.jsp");
                                request.setAttribute("Success", "Дату необходимо указать в формате дд.мм.гггг!");
                                dispatcher.forward(request, response);
                            }
                        } catch (SQLException | ClassNotFoundException | ServletException | IOException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/master_add_customers_visit.jsp");
                        request.setAttribute("Success", "Необходимо заполнить все поля!");
                        dispatcher.forward(request, response);
                    }
                } else if (request.getParameter("Back") != null) { // если нажата кнопка назад
                    request.getRequestDispatcher("/WEB-INF/view/master_main_page.jsp").forward(request, response);
                }
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response); // иначе перенаправить на страницу логина
        }
    }
}
