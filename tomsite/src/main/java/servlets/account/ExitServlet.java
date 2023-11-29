package servlets.account;

import interfaces.BucketService;
import repository.StoreRepositoryJdbc;
import service.BucketServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ExitServlet extends HttpServlet {
    private BucketService bucketCookie;
    private StoreRepositoryJdbc storeRepository;

    @Override
    public void init(ServletConfig config) {
        bucketCookie = new BucketServiceImpl();
        storeRepository = (StoreRepositoryJdbc) config.getServletContext().getAttribute("storeRep");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] inputCookie = (request.getCookies());
        String tags = bucketCookie.getProd(inputCookie);
        List<String> allTags = storeRepository.getProdTags();
        bucketCookie.returnOriginalCount(tags, allTags, storeRepository);
        bucketCookie.delAllBucketCookie(response);

        HttpSession httpSession = request.getSession(false);
        httpSession.setAttribute("authenticated", false);
        httpSession.setAttribute("isAdmin", false);

        request.getRequestDispatcher("log_or_reg.html").forward(request, response);
    }
}
