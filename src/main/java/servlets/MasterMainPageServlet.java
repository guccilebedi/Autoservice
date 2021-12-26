package servlets;

import model.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static java.util.Objects.nonNull;

public class MasterMainPageServlet extends HttpServlet {

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
}

