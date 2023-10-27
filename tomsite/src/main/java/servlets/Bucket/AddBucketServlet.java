package servlets.Bucket;

import models.Product;
import interfaces.BucketCookie;
import repository.BucketCookieJdbc;
import repository.StoreRepository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBucketServlet extends HttpServlet {

    private StoreRepository storeRepository;
    private BucketCookie bucketCookie;
    int i;

    @Override
    public void init() {
        storeRepository = new StoreRepository();
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
        request.setAttribute("tag", tag);
        request.setAttribute("name", product.getName());
        request.setAttribute("price", product.getPrice());
        request.setAttribute("description", product.getDescription());
        request.setAttribute("img", product.getImgSource());

        String count = Integer.toString(i);
        bucketCookie.addCookie("count", count, response);
        i++;

        request.getRequestDispatcher("/jsp/product.jsp").forward(request, response);
    }

}