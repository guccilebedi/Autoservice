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

import static java.util.Objects.nonNull;

public class DeleteAccountServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) // метод get
            throws ServletException, IOException {
                final HttpSession session = request.getSession();
                if (nonNull(session)) { // проверка того, что пользователь авторизован
                    if (session.getAttribute("role") == Role.MANAGER) { // если менеджер - перенаправить на страницу удаления аккаунта
                        request.getRequestDispatcher("/WEB-INF/view/delete_account.jsp").forward(request, response);
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
                final String userFullName = request.getParameter("userFullName"); // получить введённые ФИО
                if (request.getParameter("Delete") != null) { // если нажата кнопка удалить
                    if (!userFullName.equals("")) { // проверка на заполнение всех полей
                        try {
                            Class.forName("org.postgresql.Driver");
                            Connection connection = DBConnector.connect();
                            AuthorizationService authorizationService = new AuthorizationService(connection);
                            authorizationService.removeUser(new Manager((String) session.getAttribute("fullName")), userFullName);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/delete_account.jsp");
                            request.setAttribute("Success", "Учётная запись пользователя " + userFullName + " успешно удалена!");
                            dispatcher.forward(request, response);
                        } catch (SQLException | ClassNotFoundException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/delete_account.jsp");
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