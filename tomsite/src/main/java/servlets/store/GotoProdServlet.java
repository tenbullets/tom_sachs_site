package servlets.store;

import models.Product;
import repository.StoreRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/gotoProd")
public class GotoProdServlet extends HttpServlet {
    private StoreRepository storeRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        storeRepository = (StoreRepository) config.getServletContext().getAttribute("storeRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name, price, description, img, tag, countOfProd;

        String productTag = request.getParameter("htmlContent");
        Product product = storeRepository.getProduct(productTag);

        name = product.getName();
        price = String.valueOf(product.getPrice());
        description = product.getDescription();
        tag = product.getTag();
        countOfProd = String.valueOf(product.getCount());

//        System.out.println("tag " + tag);


        List<String> imgs = storeRepository.getImgs(tag, storeRepository.getImgsSource(tag));

//        for (int i = 0; i < imgs.size(); i++) {
//            System.out.println("///" + i);
//            System.out.println(imgs.get(i));
//        }

        request.setAttribute("tag", tag);
        request.setAttribute("name", name);
        request.setAttribute("price", price);
        request.setAttribute("count", countOfProd);
        request.setAttribute("description", description);
        request.setAttribute("imgs", imgs);
        request.getRequestDispatcher("/jsp/product.jsp").forward(request, response);
    }

}