package servlets.bucket;

import interfaces.BucketCookie;
import repository.BucketCookieJdbc;
import repository.StoreRepository;

import javax.servlet.ServletConfig;
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
    private StoreRepository storeRepository;

    @Override
    public void init(ServletConfig config) {
        bucketCookie = new BucketCookieJdbc();
        storeRepository = (StoreRepository) config.getServletContext().getAttribute("storeRep");
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
        request.setAttribute("storeRep", storeRepository);
        request.getRequestDispatcher("/jsp/bucket.jsp").forward(request, response);
    }

}