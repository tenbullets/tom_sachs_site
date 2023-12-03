package servlets.orders;

import interfaces.OrdersRepository;
import interfaces.StoreRepository;
import models.Order;
import models.Product;
import repository.StoreRepositoryJdbc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/gotoOrder")
public class GotoOrderServlet extends HttpServlet {
    private OrdersRepository ordersRepository;
    private StoreRepository storeRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ordersRepository = (OrdersRepository) config.getServletContext().getAttribute("orderRep");
        storeRepository = (StoreRepositoryJdbc) config.getServletContext().getAttribute("storeRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderUUID = request.getParameter("uuid");

        Order order = ordersRepository.getOrder(orderUUID);

        String[] tags = order.getOrderList().split("_");
        List<Product> products = new ArrayList<>();
        for (String tag : tags) products.add(storeRepository.getProduct(tag));

        request.setAttribute("order", order);
        request.setAttribute("prod", products);
        request.getRequestDispatcher("/jsp/order.jsp").forward(request, response);
    }

}
