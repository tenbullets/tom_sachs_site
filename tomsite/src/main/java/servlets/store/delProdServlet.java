package servlets.store;

import models.Product;
import repository.StoreRepository;
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
public class delProdServlet extends HttpServlet {
    StoreRepository storeRepository;
    ServletConfig config;
    StoreRepository storeRepository1;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        storeRepository = (StoreRepository) config.getServletContext().getAttribute("storeRep");
        storeRepository1 = (StoreRepository) config.getServletContext().getAttribute("storeRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("prodTag");
        if (storeRepository.findProduct(tag)) {
            storeRepository.delProduct(tag, storeRepository.getImgsSource(tag));
        }

        List<Product> products;

        try {
            products = storeRepository1.getAllProduct();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ServletContext servletContext = config.getServletContext();
        servletContext.setAttribute("products", products);

        request.getRequestDispatcher("/allProd").forward(request, response);
    }
}
