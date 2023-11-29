package servlets.store;

import models.Product;
import service.AddServiceImpl;
import repository.StoreRepositoryJdbc;
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
public class AddProdServlet extends HttpServlet {
    private ServletConfig config;
    private StoreRepositoryJdbc storeRepository;
    private StoreRepositoryJdbc updateRepository;
    private AddServiceImpl service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        storeRepository = (StoreRepositoryJdbc) config.getServletContext().getAttribute("storeRep");
        updateRepository = (StoreRepositoryJdbc) config.getServletContext().getAttribute("storeRep");
        service = new AddServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("addProd.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String tag = request.getParameter("tag");
        String price = request.getParameter("price");
        String desc = request.getParameter("desc");
        String count = request.getParameter("count");

        List<String> info = new ArrayList<>();
        info.add(name); info.add(tag); info.add(price); info.add(desc); info.add(count);

        Part img1 = request.getPart("img1");
        Part img2 = request.getPart("img2");
        Part img3 = request.getPart("img3");
        Part img4 = request.getPart("img4");

        List<Part> imgs = new ArrayList<>();
        imgs.add(img1); imgs.add(img2); imgs.add(img3); imgs.add(img4);

        if(service.infoCheck(info) && service.isDigit(price) && service.imgCheck(imgs)) {

            storeRepository.saveProd
            (
                    service.setFormat(name),
                    tag,
                    price,
                    service.toHtmlFormat(desc),
                    service.getDate(),
                    service.getImgsData(imgs),
                    count
            );

            List<Product> products = updateRepository.getAllProduct();
            ServletContext servletContext = config.getServletContext();
            servletContext.setAttribute("products", products);

            response.sendRedirect("addProd.html");
        } else {

            String result = null, status = "Товар не добавлен";

            if(!service.isDigit(price)) result = price + " не является числом";
            if(!service.isDigit(count)) result = count + " не является числом";
            if(name.isEmpty() || price.isEmpty() || desc.isEmpty() || tag.isEmpty() || count.isEmpty()) result = "Вы оставили поля пустыми";
            if(!service.imgCheck(imgs)) result = "Размер одного из фото превышает 10 Мбайт или отсутствует фото";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }
    }

}
