package servlets.bucket;

import interfaces.BucketCookie;
import repository.BucketCookieJdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/del")
public class DelBucketServlet extends HttpServlet {
    private BucketCookie bucketCookie;

    @Override
    public void init() {
        bucketCookie = new BucketCookieJdbc();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("delProd");
        Cookie[] cookies = request.getCookies();

        // обновляем куку товаров
        String value = bucketCookie.getProd(cookies);
        String[] prod = value.split("_");

        String newProd = bucketCookie.delProd(prod, tag);
        String[] prodToBucket = newProd.split("_");

        bucketCookie.addCookie("prod", newProd, response);

        // обновляем куку кол-ва товаров
        int countOfProd = Integer.parseInt(bucketCookie.getCount(cookies));
        countOfProd--;
        String resCountOfProd = String.valueOf(countOfProd);

        bucketCookie.addCookie("count", resCountOfProd, response);

        // обновляем этот щит
        request.setAttribute("products", prodToBucket);
        request.getRequestDispatcher("/jsp/bucket.jsp").forward(request, response);
    }

}