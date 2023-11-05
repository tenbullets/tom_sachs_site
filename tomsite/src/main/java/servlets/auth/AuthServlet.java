package servlets.auth;

import interfaces.UsersRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession httpSession = request.getSession(true);

        String result, status, name, email;

        if(username.isEmpty() || password.isEmpty()) {
            httpSession.setAttribute("authenticated", false);
            result = "Проверте введенные данные"; status = "Авторизация провалена";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }

        if(usersRepository.isAdmin(username, password)) {
            httpSession.setAttribute("isAdmin", true);
            httpSession.setAttribute("username", username);

            request.setAttribute("adminName", username);
            request.setAttribute("adminEmail", usersRepository.returnEmail(username));
            request.getRequestDispatcher("/jsp/service.jsp").forward(request, response);
        } else {

            if (usersRepository.findUser(username, password)) {
                httpSession.setAttribute("authenticated", true);
                httpSession.setAttribute("username", username);

                name = username; email = usersRepository.returnEmail(name);

                request.setAttribute("username", name);
                request.setAttribute("user_email", email);
                request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);
            } else {
                httpSession.setAttribute("authenticated", false);
                result = "Пользователь " + username + " не найден, проверте пароль и имя пользователя"; status = "Авторизация провалена";

                request.setAttribute("resultOfAut", result);
                request.setAttribute("status", status);
                request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
            }

        }
    }


}