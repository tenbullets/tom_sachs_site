package servlets.aut;

import interfaces.UsersRepository;
import repository.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";
    private UsersRepository usersRepository;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            usersRepository = new UsersRepositoryJdbcImpl(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession httpSession = request.getSession(true);

        String result, status;

        if(username.isEmpty() || password.isEmpty()) {
            httpSession.setAttribute("authenticated", false);
            result = "Проверте введенные данные";
            status = "Авторизация провалена";

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
                String name, email;
                httpSession.setAttribute("authenticated", true);
                httpSession.setAttribute("username", username);

                name = username;
                email = usersRepository.returnEmail(name);

                request.setAttribute("username", name);
                request.setAttribute("user_email", email);
                request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);
            } else {
                httpSession.setAttribute("authenticated", false);
                result = "Пользователь " + username + " не найден, проверте пароль и имя пользователя";
                status = "Авторизация провалена";

                request.setAttribute("resultOfAut", result);
                request.setAttribute("status", status);
                request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
            }

        }
    }
}