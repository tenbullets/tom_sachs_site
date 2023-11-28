package servlets.store;

import models.Product;
import repository.StoreRepositoryJdbc;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/delProd")
public class DelProdServlet extends HttpServlet {
    StoreRepositoryJdbc storeRepository;
    ServletConfig config;
    StoreRepositoryJdbc storeRepository1;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        storeRepository = (StoreRepositoryJdbc) config.getServletContext().getAttribute("storeRep");
        storeRepository1 = (StoreRepositoryJdbc) config.getServletContext().getAttribute("storeRep");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("prodTag");

        if (storeRepository.findProduct(tag)) {
            storeRepository.delProduct(tag, storeRepository.getImgsSource(tag));

            List<Product> products = storeRepository1.getAllProduct();
            ServletContext servletContext = config.getServletContext();
            servletContext.setAttribute("products", products);

            request.getRequestDispatcher("/allProd").forward(request, response);
        } else {
            String result = "Товар не был удален из базы данных", status = "Проблемы на сервере";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }
    }

}
