package servlets.exhs;

import models.Exh;
import repository.ExhRepositoryJdbc;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/gotoExh")
public class GotoExhServlet extends HttpServlet {
    ExhRepositoryJdbc repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        repository = (ExhRepositoryJdbc) config.getServletContext().getAttribute("ExhRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("tag");

        if(repository.findExh(tag)) {
            Exh exh = repository.getExh(tag);

            String name = exh.getName();
            String eventd = exh.getEventDates();
            String place = exh.getPlace();
            String sDesc = exh.getS_description();
            String desc = exh.getDescription();
            List<String> caps = repository.getCaps(tag);
            List<String> imgs = repository.getImgs(tag, repository.getImgsSource(tag));

            request.setAttribute("name", name);
            request.setAttribute("eventd", eventd);
            request.setAttribute("place", place);
            request.setAttribute("sDesc", sDesc);
            request.setAttribute("desc", desc);
            request.setAttribute("caps", caps);
            request.setAttribute("imgs", imgs);

            request.getRequestDispatcher("jsp/exh.jsp").forward(request, response);

        } else {
            String result = "К сожалению выставка не найдена, попробуйте позже", status = "Неполадки на сервере";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }

    }
}
