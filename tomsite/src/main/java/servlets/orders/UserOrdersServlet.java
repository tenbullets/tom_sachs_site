package servlets.orders;

import interfaces.OrdersRepository;
import interfaces.UsersRepository;
import models.Order;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/userOrders")
public class UserOrdersServlet extends HttpServlet {
    private OrdersRepository ordersRepository;
    private UsersRepository usersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ordersRepository = (OrdersRepository) config.getServletContext().getAttribute("orderRep");
        usersRepository = (UsersRepository) config.getServletContext().getAttribute("userRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession(true);
        String username = (String) httpSession.getAttribute("username");
        String id = usersRepository.returnId(username);

        List<Order> orders = ordersRepository.getOrders(id);

        request.setAttribute("ordersList", orders);
        request.getRequestDispatcher("/jsp/orders.jsp").forward(request, response);
    }

}
