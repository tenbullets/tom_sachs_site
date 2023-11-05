package servlets.admin;

import models.Product;
import repository.StoreRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/allProd")
public class AllProdServlet extends HttpServlet {
    StoreRepository repository;

    @Override
    public void init() throws ServletException {
        repository = new StoreRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = repository.getAllProduct();

        request.setAttribute("prodList", products);
        request.getRequestDispatcher("/jsp/prodList.jsp").forward(request,response);

    }
}
