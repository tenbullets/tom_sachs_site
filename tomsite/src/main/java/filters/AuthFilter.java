package filters;

import interfaces.UsersRepository;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

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

        Boolean isAuthenticated = false;
        Boolean isAdmin = false;
        Boolean sessionExists = session != null;
        Boolean isLoginPage = request.getRequestURI().equals("/tomsite_war/introAuth");
        Boolean isRegPage = request.getRequestURI().equals("/tomsite_war/introReg");
        Boolean isExitPage = request.getRequestURI().equals("/tomsite_war/exitPage");
        Boolean isBasketPage = request.getRequestURI().equals("/tomsite_war/addBucket");

        String username = (String) session.getAttribute("username");

        if (sessionExists) {
            isAuthenticated = (Boolean) session.getAttribute("authenticated");
            isAdmin = (Boolean) session.getAttribute("isAdmin");
            if (isAuthenticated == null) isAuthenticated = false;
            if (isAdmin == null) isAdmin = false;
        }

        if (isRegPage) filterChain.doFilter(request, response);
        if (isExitPage) filterChain.doFilter(request, response);

        if (isAdmin && isLoginPage) {
            request.setAttribute("adminName", username);
            request.setAttribute("adminEmail", usersRepository.returnEmail(username));
            request.getRequestDispatcher("/jsp/service.jsp").forward(request, response);
        }

        if (!isAuthenticated && isLoginPage) {
            filterChain.doFilter(request, response);
        } else if (isAuthenticated && isLoginPage) {
            request.getRequestDispatcher("/goToLog").forward(request, response);
        }

        if (isBasketPage && !isAuthenticated) {
            String result = "Прежде чем совершать покупки, войдите или зарегестрируйтесь. Для этого перейдите в раздел «Аккаунт».";
            String status = "Вы не вошли";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        } else if (isBasketPage && isAuthenticated) {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
