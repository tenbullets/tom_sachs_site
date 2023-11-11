package servlets.bucket;

import models.Product;
import interfaces.BucketCookie;
import repository.BucketCookieJdbc;
import repository.StoreRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AddBucketServlet extends HttpServlet {

    private StoreRepository storeRepository;
    private BucketCookie bucketCookie;
    int i;

    @Override
    public void init(ServletConfig config) {
        storeRepository = (StoreRepository) config.getServletContext().getAttribute("storeRep");
        bucketCookie = new BucketCookieJdbc();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("product");
        Cookie[] inputCookie = (request.getCookies());

        if(!bucketCookie.cookieIsReal(inputCookie, "prod")) {
            bucketCookie.addCookie("prod", tag, response);
            i = 1;
        } else {
            String value = bucketCookie.getProd(inputCookie) + "_" + tag;
            bucketCookie.addCookie("prod", value, response);
            i = bucketCookie.getCountOfProducts(value);
        }

        Product product = storeRepository.getProduct(tag);
        List<String> imgs = storeRepository.getImgs(tag, storeRepository.getImgsSource(tag));

        request.setAttribute("tag", tag);
        request.setAttribute("name", product.getName());
        request.setAttribute("price", String.valueOf(product.getPrice()));
        request.setAttribute("description", product.getDescription());
        request.setAttribute("imgs", imgs);
        request.setAttribute("count", String.valueOf(product.getCount()));

        String count = Integer.toString(i);
        bucketCookie.addCookie("count", count, response);
        i++;

        request.getRequestDispatcher("/jsp/product.jsp").forward(request, response);
    }

}