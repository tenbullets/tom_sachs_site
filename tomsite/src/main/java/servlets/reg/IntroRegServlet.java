package servlets.reg;

import service.RegServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IntroRegServlet extends HttpServlet {
    RegServiceImpl regService;

    @Override
    public void init() throws ServletException {
        regService = new RegServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        regService.delProdCookie(response);
        request.getRequestDispatcher("registration.html").forward(request, response);
    }
}