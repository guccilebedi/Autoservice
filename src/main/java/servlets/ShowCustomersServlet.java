package servlets;

import model.Manager;
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

import static java.util.Objects.nonNull;

public class ShowCustomersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // метод get
        final HttpSession session = request.getSession();
        if (nonNull(session)) { // проверка того, что пользователь авторизован
            if (session.getAttribute("role") == Role.MANAGER) {
                request.getRequestDispatcher("/WEB-INF/view/manager_show_customers.jsp").forward(request, response);
            } else if (session.getAttribute("role") == Role.MASTER) {
                request.getRequestDispatcher("/WEB-INF/view/master_show_customers.jsp").forward(request, response);
            }
        } else { // иначе перенаправить на страницу входа
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // метод post
        final HttpSession session = request.getSession();
        if (nonNull(session)) { // проверка того, что пользователь авторизован
            if (session.getAttribute("role") == Role.MANAGER) {
                if (request.getParameter("Show") != null) {
                    Manager manager = new Manager((String) session.getAttribute("fullName"));
                    final String masterFullName = request.getParameter("masterFullName");
                    try {
                        Class.forName("org.postgresql.Driver");
                        Connection connection = DBConnector.connect();
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/manager_show_customers.jsp");
                        request.setAttribute("List", manager.getCustomers(connection, masterFullName));
                        dispatcher.forward(request, response);
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                } else if (request.getParameter("Back") != null) {
                    request.getRequestDispatcher("/WEB-INF/view/manager_main_page.jsp").forward(request, response);
                }
            } else if (session.getAttribute("role") == Role.MASTER) {
                if (request.getParameter("Back") != null) {
                    request.getRequestDispatcher("/WEB-INF/view/master_main_page.jsp").forward(request, response);
                }
            }
        } else { // иначе перенаправить на страницу входа
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }
}
