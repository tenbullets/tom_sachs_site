package servlets.exhs;

import models.Exh;
import repository.ExhRepositoryJdbc;
import service.AddServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
public class AddExhServlet extends HttpServlet {
    private ServletConfig config;
    private AddServiceImpl service;
    private ExhRepositoryJdbc repository;
    private ExhRepositoryJdbc updateRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        service = new AddServiceImpl();
        repository = (ExhRepositoryJdbc) config.getServletContext().getAttribute("ExhRep");
        updateRepository = (ExhRepositoryJdbc) config.getServletContext().getAttribute("ExhRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("addExh.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String tag = request.getParameter("tag");
        String place = request.getParameter("place");
        String eventDates = request.getParameter("date");
        String sDesc = request.getParameter("sDesc");
        String desc = request.getParameter("Desc");

        List<String> info = new ArrayList<>();
        info.add(name); info.add(tag); info.add(place); info.add(eventDates); info.add(sDesc); info.add(desc);

        String cap1 = service.setFormat(request.getParameter("cap-1"));
        String cap2 = service.setFormat(request.getParameter("cap-2"));
        String cap3 = service.setFormat(request.getParameter("cap-3"));
        String cap4 = service.setFormat(request.getParameter("cap-4"));
        String cap5 = service.setFormat(request.getParameter("cap-5"));
        String cap6 = service.setFormat(request.getParameter("cap-6"));

        List<String> caps = new ArrayList<>();
        caps.add(cap1); caps.add(cap2); caps.add(cap3); caps.add(cap4); caps.add(cap5); caps.add(cap6);

        Part img1 = request.getPart("img1");
        Part img2 = request.getPart("img2");
        Part img3 = request.getPart("img3");
        Part img4 = request.getPart("img4");
        Part img5 = request.getPart("img5");
        Part img6 = request.getPart("img6");

        List<Part> imgs = new ArrayList<>();
        imgs.add(img1); imgs.add(img2); imgs.add(img3); imgs.add(img4); imgs.add(img5); imgs.add(img6);


        if(service.infoCheck(info) && service.capsCheck(caps) && service.imgCheck(imgs)) {
            repository.saveExh
            (
                    service.setFormat(name), tag,
                    service.setFormat(place),
                    service.getDate(), eventDates,
                    service.setFormat(sDesc),
                    service.toHtmlFormat(desc),
                    service.getCapsData(caps),
                    service.getImgsData(imgs)
            );

            List<Exh> list = updateRepository.getExhList();
            ServletContext servletContext = config.getServletContext();
            servletContext.setAttribute("ExhRep", list);

            response.sendRedirect("addExh.html");
        } else {
            String result = null, status = "Выставка не добавлена";
            if(service.infoCheck(info) || service.capsCheck(caps)) result = "Вы оставили поля пустыми";
            if(!service.imgCheck(imgs)) result = "Размер одного из фото превышает 10 Мбайт или отсутствует фото";
            if(!service.capsCheck(caps)) result = "Вы не добавили подпись к фото";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }

    }
}
