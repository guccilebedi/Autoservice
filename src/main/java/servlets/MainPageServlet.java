package servlets;

import model.Manager;
import model.Master;
import model.Role;
import service.AuthorizationService;
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

import static java.util.Objects.nonNull;

public class MainPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // метод get
        final HttpSession session = request.getSession();
        if (nonNull(session)) { // проверка того, что пользователь авторизован
            if (session.getAttribute("role") == Role.MANAGER) { // если менеджер - перенаправить на его главную страницу
                request.getRequestDispatcher("/WEB-INF/view/manager_main_page.jsp").forward(request, response);
            } else if (session.getAttribute("role") == Role.MASTER) { // если мастер - перенаправить на его главную страницу
                request.getRequestDispatcher("/WEB-INF/view/master_main_page.jsp").forward(request, response);
            }
        } else { // иначе перенаправить на страницу входа
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // метод post
        final HttpSession session = request.getSession();
        if (nonNull(session)) { // проверка того, что пользователь авторизован
            if (session.getAttribute("role") == Role.MANAGER) {
                if (request.getParameter("Register") != null) { // если нажата кнопка зарегистрировать пользователя
                    request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
                } else if (request.getParameter("Delete") != null) { // если нажата кнопка удалить учётную запись
                    request.getRequestDispatcher("/WEB-INF/view/delete_account.jsp").forward(request, response);
                } else if (request.getParameter("AddCustomer") != null) { // если нажата кнопка добавить запись о визите клиента к мастеру
                    request.getRequestDispatcher("/WEB-INF/view/manager_add_customers_visit.jsp").forward(request, response);
                } else if (request.getParameter("DeleteCustomer") != null) { // если нажата кнопка добавить запись о визите клиента к мастеру
                    request.getRequestDispatcher("/WEB-INF/view/delete_customers_visit.jsp").forward(request, response);
                } else if (request.getParameter("Show") != null) {
                    request.getRequestDispatcher("/WEB-INF/view/manager_show_customers.jsp").forward(request, response);
                }
            } else if (session.getAttribute("role") == Role.MASTER) { // если нажата кнопка добавить запись о визите клиента
                if (request.getParameter("Add") != null) {
                    request.getRequestDispatcher("/WEB-INF/view/master_add_customers_visit.jsp").forward(request, response);
                } else if (request.getParameter("Show") != null) {
                    try {
                        Class.forName("org.postgresql.Driver");
                        Connection connection = DBConnector.connect();
                        Master master = new Master((String) session.getAttribute("fullName"));
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/master_show_customers.jsp");
                        request.setAttribute("List", master.getCustomers(connection, master.fullName));
                        dispatcher.forward(request, response);
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else { // иначе перенаправить на страницу входа
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }
}

