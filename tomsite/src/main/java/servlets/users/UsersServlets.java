package servlets.users;

import interfaces.UsersRepository;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UsersServlets extends HttpServlet {
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List users, admins;
        try {
            users = usersRepository.allUsers();
            admins = usersRepository.allAdmins();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        request.setAttribute("users", users);
        request.setAttribute("admins", admins);
        request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
    }

}
