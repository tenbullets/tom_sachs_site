package servlets;

import models.Product;
import repository.StoreRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/gotoProd")
public class GotoProdServlet extends HttpServlet {
    private StoreRepository storeRepository;

    @Override
    public void init(){
        storeRepository = new StoreRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name, price, description, img, tag;

        String pr = request.getParameter("htmlContent");

        Product product = storeRepository.getProduct(pr);

        name = product.getName();
        price = product.getPrice();
        description = product.getDescription();
        img = product.getImgSource();
        tag = product.getTag();

        request.setAttribute("tag", tag);
        request.setAttribute("name", name);
        request.setAttribute("price", price);
        request.setAttribute("description", description);
        request.setAttribute("img", img);
        request.getRequestDispatcher("/jsp/product.jsp").forward(request, response);
    }

}