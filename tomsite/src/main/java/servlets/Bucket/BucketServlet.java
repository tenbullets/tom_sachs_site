package servlets.Bucket;

import interfaces.BucketCookie;
import repository.BucketCookieJdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bucketPage")
public class BucketServlet extends HttpServlet {

    private BucketCookie bucketCookie;

    @Override
    public void init() {
        bucketCookie = new BucketCookieJdbc();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String[] prod = new String[0];

        if(bucketCookie.cookieIsReal(cookies, "count") && bucketCookie.cookieIsReal(cookies, "prod")) {
            String value = bucketCookie.getProd(cookies);
            prod = value.split("_");
        }

        request.setAttribute("products", prod);
        request.getRequestDispatcher("/jsp/bucket.jsp").forward(request, response);
    }

}