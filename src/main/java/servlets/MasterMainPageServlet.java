package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/master_main_page")
public class MasterMainPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // метод get
        request.getRequestDispatcher("/WEB-INF/view/master_main_page.jsp").forward(request, response); // переводит на страницу /master_main_page
    }
}

