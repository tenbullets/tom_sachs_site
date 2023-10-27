package servlets.reg;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class IntroRegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        delProdCookie(response);
        request.getRequestDispatcher("registration.html").forward(request, response);
    }

    void delProdCookie(HttpServletResponse response) {
        Cookie prodCookie = new Cookie("prod", "");
        Cookie countCookie = new Cookie("count", "");
        response.addCookie(prodCookie);
        response.addCookie(countCookie);
        prodCookie.setMaxAge(0);
        countCookie.setMaxAge(0);
    }
}