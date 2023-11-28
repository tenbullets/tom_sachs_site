package servlets.exhs;

import models.Exh;
import repository.ExhRepositoryJdbc;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/delExh")
public class DelExhServlet extends HttpServlet {
    ServletConfig config;
    ExhRepositoryJdbc repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        repository = (ExhRepositoryJdbc) config.getServletContext().getAttribute("ExhRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("tag");

        if (repository.findExh(tag)) {
            repository.delExh(tag, repository.getImgsSource(tag));
            List<Exh> exhs = repository.getExhList();
            ServletContext servletContext = config.getServletContext();
            servletContext.setAttribute("exhs", exhs);

            request.getRequestDispatcher("/exhList").forward(request, response);
        } else {
            String result = "Выставка не была удалена из базы данных", status = "Проблемы на сервере";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }
    }
}