package servlets.admin;

import interfaces.UsersRepository;
import repository.DataRepositoryJdbc;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/delUser")
public class DelUserServlet extends HttpServlet {
    private UsersRepository usersRepository;
    private DataRepositoryJdbc data;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
        data = (DataRepositoryJdbc) config.getServletContext().getAttribute("dataRep");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        try {
            if (usersRepository.findUserByName(username)) data.delUser(usersRepository.returnId(username));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/users").forward(request, response);
    }

}
