package servlets;

import model.Manager;
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
import java.util.List;

import static java.util.Objects.nonNull;

public class RegistrationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // метод get
            throws ServletException, IOException {
                final HttpSession session = request.getSession();
                if (nonNull(session)) { // проверка того, что пользователь авторизован
                    if (session.getAttribute("role") == Role.MANAGER) { // если менеджер - перенаправить на его главную страницу
                        request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
                    } else if (session.getAttribute("role") == Role.MASTER) { // если мастер - перенаправить на его главную страницу
                        request.getRequestDispatcher("/WEB-INF/view/master_main_page.jsp").forward(request, response);
                    }
                } else { // иначе перенаправить на страницу входа
                    request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
                }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  // метод post
        final HttpSession session = request.getSession();
        if (nonNull(session)) { // проверка того, что пользователь авторизован
            if (session.getAttribute("role") == Role.MANAGER) { // проверка того, что регистрирующий - менеджер
                if (request.getParameter("Register") != null) { // если нажата кнопка зарегистрировать
                final String newUserFullName = request.getParameter("newUserFullName"); // получить введённые ФИО
                final String newUserName = request.getParameter("newUserLogin"); // получить введённый логин
                final String newUserPassword = request.getParameter("newUserPassword"); // получить введённый пароль
                final String temp = request.getParameter("newUserRole"); // получить выбранную роль
                    if (!newUserFullName.equals("") && !newUserName.equals("") && !newUserPassword.equals("") && !temp.equals("")) { // проверка на заполнение всех полей
                        final Role newUserRole;
                        if (temp.equals("manager")) {
                            newUserRole = Role.MANAGER;
                        } else {
                            newUserRole = Role.MASTER;
                        }
                        try {
                            Class.forName("org.postgresql.Driver");
                            Connection connection = DBConnector.connect();
                            AuthorizationService authorizationService = new AuthorizationService(connection);
                            List result = authorizationService.login(newUserName, newUserPassword);
                            if (result.get(0) == null) { // проверка того, что в базе нет такого пользователя
                                authorizationService.addUser(new Manager((String) session.getAttribute("fullName")), newUserName, newUserPassword, newUserFullName, newUserRole);
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/register.jsp");
                                request.setAttribute("Success", "Пользователь " + newUserFullName + " успешно зарегистрирован!");
                                dispatcher.forward(request, response);
                            } else {
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/register.jsp");
                                request.setAttribute("Success", "Пользователь с такими данными уже зарегистрирован!");
                                dispatcher.forward(request, response);
                            }
                        } catch (SQLException | ClassNotFoundException | ServletException | IOException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/register.jsp");
                        request.setAttribute("Success", "Необходимо заполнить все поля!");
                        dispatcher.forward(request, response);
                    }
                } else if (request.getParameter("Back") != null) { // если нажата кнопка назад
                    request.getRequestDispatcher("/WEB-INF/view/manager_main_page.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("/WEB-INF/view/master_main_page.jsp").forward(request, response); // иначе перенаправить на главную страницу мастера
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response); // иначе перенаправить на страницу логина
        }
    }
}