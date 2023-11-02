package servlets.reg;

import interfaces.UsersRepository;
import repository.DataRepositoryJdbc;
import repository.SignUpServiceImpl;

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
    SignUpServiceImpl signUpServiceImpl;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
        data = (DataRepositoryJdbc) config.getServletContext().getAttribute("dataRep");
        signUpServiceImpl = new SignUpServiceImpl(data);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String secondPassword = request.getParameter("second_password");

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("isAdmin", false);
        httpSession.setAttribute("authenticated", false);

        String result, status;

        if (usersRepository.findUserByEmail(email)) {
            result = "Пользователь с почтой " + email + " уже зарегестрирован"; status = "Регестрация провалена";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        } else {
            if (formIsOk(username, email, password, secondPassword)) {
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
                result = "Пользователь не зарегестрирован"; status = "Регестрация провалена";

                request.setAttribute("resultOfAut", result);
                request.setAttribute("status", status);
                request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
            }
        }
    }

    public boolean formIsOk(String username, String email, String password, String secondPassword) {
        return !password.isEmpty() && !username.isEmpty() && password.equals(secondPassword) && !email.isEmpty();
    }

}