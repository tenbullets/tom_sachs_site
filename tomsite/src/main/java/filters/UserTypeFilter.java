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

        if (!isAdmin && (isUsersPage || isAddAdminPage)) {
            String result = "Вы не обладаете правами админа";
            String status = "Провалено";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        } else if (isAdmin && (isUsersPage || isAddAdminPage)) {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
