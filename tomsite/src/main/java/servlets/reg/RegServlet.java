package servlets.reg;

import interfaces.UsersRepository;
import repository.DataRepositoryJdbc;
import service.RegServiceImpl;
import service.SignUpServiceImpl;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/reg")
public class RegServlet extends HttpServlet {
    private UsersRepository usersRepository;
    private DataRepositoryJdbc data;
    private RegServiceImpl regService;
    private SignUpServiceImpl signUpServiceImpl;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
        data = (DataRepositoryJdbc) config.getServletContext().getAttribute("dataRep");
        signUpServiceImpl = new SignUpServiceImpl(data);
        regService = new RegServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String secondPassword = request.getParameter("second_password");

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("isAdmin", false);
        httpSession.setAttribute("authenticated", false);

        String result = null, status;

        if (usersRepository.findUserByEmail(email)) {
            result = "Пользователь с почтой " + email + " уже зарегистрирован"; status = "Регистрация провалена";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        } else {
            if (regService.formAttached(username, email, password, secondPassword)) {
                String uniqueID = java.util.UUID.randomUUID().toString();

                try {
                    signUpServiceImpl.signUp(username, email, password, uniqueID);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                httpSession.setAttribute("authenticated", true);
                httpSession.setAttribute("username", username);

                request.setAttribute("username", username);
                request.setAttribute("user_email", email);
                request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);
            } else {
                status = "Регистрация провалена";
                if(!regService.usernameValidate(username)) result = "Имя пользователя отсутствует или не подходит";
                if(!regService.emailValidate(email)) result = "Почта отсутствует или не является электронной почтой";
                if(!regService.passwordValidate(password)) result = "Пароль отсутствует или не подходит";
                if(!regService.passwordsMatch(password, secondPassword)) result = "Пароли не совпадают";

                request.setAttribute("resultOfAut", result);
                request.setAttribute("status", status);
                request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
            }
        }
    }

}