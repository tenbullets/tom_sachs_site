package servlets.orders;

import interfaces.OrdersRepository;
import models.Order;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrdersListServlet extends HttpServlet {
    private OrdersRepository ordersRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ordersRepository = (OrdersRepository) config.getServletContext().getAttribute("orderRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders = ordersRepository.getUsersOrders();
        request.setAttribute("ordersList", orders);
        request.getRequestDispatcher("/jsp/a_orders.jsp").forward(request, response);
    }

}
