package servlets.reg;

import interfaces.UsersRepository;
import repository.DataRepositoryJdbc;
import repository.SignUpServiceImpl;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;



@WebServlet("/adminReg")
public class AdminRegServlet extends HttpServlet {
    private UsersRepository usersRepository;
    private DataRepositoryJdbc data;
    SignUpServiceImpl signUpServiceImpl;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
        data = (DataRepositoryJdbc) config.getServletContext().getAttribute("dataRep");
        signUpServiceImpl = new SignUpServiceImpl(data);
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

        String result, status;

        if (usersRepository.findUserByEmail(email)) {
            result = "Админ с почтой " + email + " уже зарегестрирован"; status = "Регестрация провалена";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }

        if(code.equals("1234")) {
            if (!password.isEmpty() && !username.isEmpty() && password.equals(secondPassword) && !email.isEmpty()) {
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
                result = "Проверте введенные данные"; status = "Регестрация провалена";

                request.setAttribute("resultOfAut", result);
                request.setAttribute("status", status);
                request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
            }

        } else {
            result = "В предоставлении прав отказано, неверный код идентификации"; status = "Регестрация провалена";

            request.setAttribute("resultOfAut", result);
            request.setAttribute("status", status);
            request.getRequestDispatcher("/jsp/result.jsp").forward(request, response);
        }

    }
}
