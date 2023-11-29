package servlets.exhs;

import models.Exh;
import repository.ExhRepositoryJdbc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ExhListServlet extends HttpServlet {
    private ExhRepositoryJdbc repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        repository = (ExhRepositoryJdbc) config.getServletContext().getAttribute("ExhRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Exh> list = repository.getExhList();

        request.setAttribute("exhList", list);
        request.getRequestDispatcher("/jsp/exhList.jsp").forward(request,response);

    }
}
