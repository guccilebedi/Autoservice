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
        if (nonNull(session)) {
            if (session.getAttribute("role") == Role.MANAGER) {
                request.getRequestDispatcher("/WEB-INF/view/manager_main_page.jsp").forward(request, response);
            } else if (session.getAttribute("role") == Role.MASTER) {
                request.getRequestDispatcher("/WEB-INF/view/master_main_page.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
        }
    }
}

