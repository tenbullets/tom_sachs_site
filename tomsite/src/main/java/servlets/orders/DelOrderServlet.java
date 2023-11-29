package servlets.orders;

import interfaces.OrdersRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delOrder")
public class DelOrderServlet extends HttpServlet {
    private OrdersRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        repository = (OrdersRepository) config.getServletContext().getAttribute("orderRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderUUID = request.getParameter("uuid");
        if (repository.findOrder(orderUUID)) repository.delOrder(orderUUID);

        request.getRequestDispatcher("/ordersList").forward(request, response);
    }
}
