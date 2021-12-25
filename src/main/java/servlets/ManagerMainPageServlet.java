package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/manager_main_page")
public class ManagerMainPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // метод get
        request.getRequestDispatcher("/WEB-INF/view/manager_main_page.jsp").forward(request, response); // переводит на страницу /manager_main_page
    }
}

