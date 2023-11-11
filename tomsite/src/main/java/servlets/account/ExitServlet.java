package servlets.account;

import interfaces.BucketCookie;
import repository.BucketCookieJdbc;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class ExitServlet extends HttpServlet {
    private BucketCookie bucketCookie;

    @Override
    public void init() {
        bucketCookie = new BucketCookieJdbc();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        bucketCookie.delAllBucketCookie(response);
        HttpSession httpSession = request.getSession(false);
        httpSession.setAttribute("authenticated", false);
        httpSession.setAttribute("isAdmin", false);
        request.getRequestDispatcher("log_or_reg.html").forward(request, response);
    }

}
