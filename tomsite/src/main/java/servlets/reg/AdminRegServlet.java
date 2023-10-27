package servlets.reg;


import interfaces.UsersRepository;
import repository.DataRepositoryJdbc;
import repository.UsersRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


@WebServlet("/adminReg")
public class AdminRegServlet extends HttpServlet {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "qwikWell12";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/test";
    private static final java.util.UUID UUID = null;
    private UsersRepository usersRepository;
    private DataRepositoryJdbc data;

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
            data = new DataRepositoryJdbc(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("ad_registration.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String secondPassword = request.getParameter("second_password");
        String code = request.getParameter("code");

        String result;
        String status;

        if (usersRepository.findUserByEmail(email)) {
            result = "Админ с почтой " + email + " уже зарегестрирован";
            status = "Регестрация провалена";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }

        if(code.equals("1234")) {
            if (!password.isEmpty() && !username.isEmpty() && password.equals(secondPassword) && !email.isEmpty()) {
                String uniqueID = UUID.randomUUID().toString();
                data.adminSave(username, email, password, uniqueID);

                request.setAttribute("adminName", username);
                request.setAttribute("adminEmail", email);
                request.getRequestDispatcher("/jsp/service.jsp").forward(request, response);
            } else {
                result = "Проверте введенные данные";
                status = "Регестрация провалена";

                request.setAttribute("resultOfAut", result);
                request.setAttribute("status", status);
                request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
            }

        } else {
            result = "В предоставлении прав отказано, неверный код идентификации";
            status = "Регестрация провалена";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }

    }
}
