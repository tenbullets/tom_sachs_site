package servlets.bucket;

import models.Product;
import interfaces.BucketService;
import service.BucketServiceImpl;
import repository.StoreRepositoryJdbc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddBucketServlet extends HttpServlet {

    private StoreRepositoryJdbc storeRepository;
    private BucketService bucketCookie;
    int i;

    @Override
    public void init(ServletConfig config) {
        storeRepository = (StoreRepositoryJdbc) config.getServletContext().getAttribute("storeRep");
        bucketCookie = new BucketServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("product");
        String value;
        Cookie[] inputCookie = (request.getCookies());

        if(!bucketCookie.cookieIsReal(inputCookie, "prod")) {
            bucketCookie.addCookie("prod", tag, response);
            i = 1;
        } else {
            value = bucketCookie.getProd(inputCookie) + "_" + tag;
            bucketCookie.addCookie("prod", value, response);
            i = bucketCookie.getCountOfProducts(value);
        }

        Product prod = storeRepository.getProduct(tag);
        if(prod.getCount() == 0) {
            String result = "Невозможно добавить товар в корзину", status = "Провалено";
            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        } else {
            storeRepository.addProdToCart(tag);

            Product updateProd = storeRepository.getProduct(tag);
            List<String> imgs = storeRepository.getImgs(tag, storeRepository.getImgsSource(tag));

            request.setAttribute("tag", tag);
            request.setAttribute("name", updateProd.getName());
            request.setAttribute("price", String.valueOf(updateProd.getPrice()));
            request.setAttribute("description", updateProd.getDescription());
            request.setAttribute("imgs", imgs);
            request.setAttribute("count", String.valueOf(updateProd.getCount()));

            String count = Integer.toString(i);
            bucketCookie.addCookie("count", count, response);
            i++;

            request.getRequestDispatcher("/jsp/product.jsp").forward(request, response);
        }
    }

}