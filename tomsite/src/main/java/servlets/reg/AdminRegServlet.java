package servlets.reg;

import interfaces.UsersRepository;
import repository.DataRepositoryJdbc;
import service.RegServiceImpl;
import service.SignUpServiceImpl;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AdminRegServlet extends HttpServlet {
    private UsersRepository usersRepository;
    private DataRepositoryJdbc data;
    private SignUpServiceImpl signUpServiceImpl;
    private RegServiceImpl regService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
        data = (DataRepositoryJdbc) config.getServletContext().getAttribute("dataRep");
        signUpServiceImpl = new SignUpServiceImpl(data);
        regService = new RegServiceImpl();
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

        String result = null, status;

        if (usersRepository.findUserByEmail(email)) {
            result = "Админ с почтой " + email + " уже зарегистрирован"; status = "Регистрация провалена";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }

        if(code.equals("1234")) {
            if (regService.formAttached(username, email, password, secondPassword)) {
                String uniqueID = java.util.UUID.randomUUID().toString();

                try {
                    signUpServiceImpl.adminSignUp(username, email, password, uniqueID);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                request.setAttribute("adminName", username);
                request.setAttribute("adminEmail", email);
                request.getRequestDispatcher("/jsp/service.jsp").forward(request, response);
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

        } else {
            result = "В предоставлении прав отказано, неверный код идентификации"; status = "Регистрация провалена";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }
    }

}
