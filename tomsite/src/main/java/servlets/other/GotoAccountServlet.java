package servlets.other;

import interfaces.UsersRepository;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/goToLog")
public class GotoAccountServlet extends HttpServlet {
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        String email = usersRepository.returnEmail(username);

        request.setAttribute("username", username);
        request.setAttribute("user_email", email);
        request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);
    }
}