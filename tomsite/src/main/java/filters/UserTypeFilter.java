package filters;

import interfaces.UsersRepository;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserTypeFilter implements Filter {
    private UsersRepository usersRepository;

    @Override
    public void init(FilterConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        Boolean isAdmin = false;
        Boolean isAuthenticated = false;
        Boolean sessionExists = session != null;

        Boolean isLoginPage = request.getRequestURI().equals("/tomsite_war/introAuth");

        Boolean isUsersPage = request.getRequestURI().equals("/tomsite_war/users");
        Boolean isAddAdminPage = request.getRequestURI().equals("/tomsite_war/addAdmin");
        Boolean isProdListPage = request.getRequestURI().equals("/tomsite_war/allProd");
        Boolean isAddProdPage = request.getRequestURI().equals("/tomsite_war/addProd");
        Boolean isExhListPage = request.getRequestURI().equals("/tomsite_war/exhList");
        Boolean isAddExhPage = request.getRequestURI().equals("/tomsite_war/addExh");
        Boolean isOrdersListAdPage = request.getRequestURI().equals("/tomsite_war/ordersList");
        Boolean isOrderAdPage = request.getRequestURI().equals("/tomsite_war/gotoOrderAd");

        if (sessionExists) {
            isAuthenticated = (Boolean) session.getAttribute("authenticated");
            if (isAuthenticated == null) isAuthenticated = false;
            isAdmin = (Boolean) session.getAttribute("isAdmin");
            if (isAdmin == null) isAdmin = false;
        }

        if (!isAuthenticated && !isAdmin && isLoginPage) {
            filterChain.doFilter(request, response);
        }

        if (isAdmin && isLoginPage) {
            String username = (String) session.getAttribute("username");
            request.setAttribute("adminName", username);
            request.setAttribute("adminEmail", usersRepository.returnEmail(username));
            request.getRequestDispatcher("/jsp/service.jsp").forward(request, response);
        }

        final boolean adminPages = isUsersPage || isAddAdminPage || isProdListPage || isAddProdPage || isExhListPage || isAddExhPage || isOrdersListAdPage || isOrderAdPage;
        if (!isAdmin && adminPages) {
            String result = "Вы не обладаете необходимыми правами доступа";
            String status = "Провалено";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        } else if (isAdmin && adminPages) {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {}

}